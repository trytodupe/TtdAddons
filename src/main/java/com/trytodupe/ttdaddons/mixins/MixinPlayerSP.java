package com.trytodupe.ttdaddons.mixins;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import com.trytodupe.ttdaddons.events.EventMotionUpdate;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

@Mixin(value = {EntityPlayerSP.class}, priority = 1)
public abstract class MixinPlayerSP extends MixinAbstractClientPlayer{
    @Shadow
    public abstract void setSprinting(boolean paramBoolean);

    @Shadow
    public abstract void onCriticalHit(Entity paramEntity);

    @Shadow
    public abstract void onEnchantmentCritical(Entity paramEntity);

    @Shadow
    public abstract void addStat(StatBase paramStatBase, int paramInt);

    @Shadow
    public abstract boolean isSneaking();

    @Shadow
    private boolean serverSprintState;

    @Shadow
    private boolean serverSneakState;

    @Shadow
    @Final
    public NetHandlerPlayClient sendQueue;

    @Shadow
    protected abstract boolean isCurrentViewEntity();

    @Shadow
    private double lastReportedPosX;

    @Shadow
    private double lastReportedPosY;

    @Shadow
    private double lastReportedPosZ;

    @Shadow
    private float lastReportedYaw;

    @Shadow
    private float lastReportedPitch;

    @Shadow
    private int positionUpdateTicks;

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        return false;
    }

    @Override
    public void attackTargetEntityWithCurrentItem(Entity targetEntity)
    {
        if (!net.minecraftforge.common.ForgeHooks.onPlayerAttackTarget((EntityPlayer)mc.thePlayer, targetEntity)) return;
        if (targetEntity.canAttackWithItem())
        {
            if (!targetEntity.hitByEntity((Entity)mc.thePlayer))
            {
                float f = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
                int i = 0;
                float f1 = 0.0F;

                if (targetEntity instanceof EntityLivingBase)
                {
                    f1 = EnchantmentHelper.func_152377_a(getHeldItem(), ((EntityLivingBase)targetEntity).getCreatureAttribute());
                }
                else
                {
                    f1 = EnchantmentHelper.func_152377_a(getHeldItem(), EnumCreatureAttribute.UNDEFINED);
                }

                i = i + EnchantmentHelper.getKnockbackModifier((EntityPlayer)((Object)this));

                if (isSprinting())
                {
                    ++i;
                }

                if (f > 0.0F || f1 > 0.0F)
                {
                    boolean flag = this.fallDistance > 0.0F && !this.onGround && !isOnLadder() && !isInWater() && !isPotionActive(Potion.blindness) && this.ridingEntity == null && targetEntity instanceof EntityLivingBase;

                    if (flag && f > 0.0F)
                    {
                        f *= 1.5F;
                    }

                    f = f + f1;
                    boolean flag1 = false;
                    int j = EnchantmentHelper.getFireAspectModifier((EntityPlayer)((Object)this));

                    if (targetEntity instanceof EntityLivingBase && j > 0 && !targetEntity.isBurning())
                    {
                        flag1 = true;
                        targetEntity.setFire(1);
                    }

                    double d0 = targetEntity.motionX;
                    double d1 = targetEntity.motionY;
                    double d2 = targetEntity.motionZ;
                    boolean flag2 = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)mc.thePlayer), f);

                    if (flag2)
                    {
                        if (i > 0)
                        {
                            targetEntity.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
                            if (ConfigHandler.keepSprint) {
                                if (targetEntity.isSprinting()) {
                                    mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                                    mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                                }
                            } else {
                                this.motionX *= 0.6D;
                                this.motionZ *= 0.6D;
                                setSprinting(false);
                            }
                        }
                        if (targetEntity instanceof EntityPlayerMP && targetEntity.velocityChanged)
                        {
                            ((EntityPlayerMP)targetEntity).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(targetEntity));
                            targetEntity.velocityChanged = false;
                            targetEntity.motionX = d0;
                            targetEntity.motionY = d1;
                            targetEntity.motionZ = d2;
                        }

                        if (flag)
                        {
                            onCriticalHit(targetEntity);
                        }

                        if (f1 > 0.0F)
                        {
                            onEnchantmentCritical(targetEntity);
                        }

                        if (f >= 18.0F)
                        {
                            triggerAchievement(AchievementList.overkill);
                        }

                        setLastAttacker(targetEntity);

                        if (targetEntity instanceof EntityLivingBase)
                        {
                            EnchantmentHelper.applyThornEnchantments((EntityLivingBase)targetEntity, (Entity)mc.thePlayer);
                        }

                        EnchantmentHelper.applyArthropodEnchantments((EntityPlayer)((Object)this), targetEntity);
                        ItemStack itemstack = getCurrentEquippedItem();
                        Entity entity = targetEntity;

                        if (targetEntity instanceof EntityDragonPart)
                        {
                            IEntityMultiPart ientitymultipart = ((EntityDragonPart)targetEntity).entityDragonObj;

                            if (ientitymultipart instanceof EntityLivingBase)
                            {
                                entity = (EntityLivingBase)ientitymultipart;
                            }
                        }

                        if (itemstack != null && entity instanceof EntityLivingBase)
                        {
                            itemstack.hitEntity((EntityLivingBase)entity, (EntityPlayer)mc.thePlayer);

                            if (itemstack.stackSize <= 0)
                            {
                                destroyCurrentEquippedItem();
                            }
                        }

                        if (targetEntity instanceof EntityLivingBase)
                        {
                            addStat(StatList.damageDealtStat, Math.round(f * 10.0F));

                            if (j > 0)
                            {
                                targetEntity.setFire(j * 4);
                            }
                        }

                        addExhaustion(0.3F);
                    }
                    else if (flag1)
                    {
                        targetEntity.extinguish();
                    }
                }
            }
        }
    }

    @Overwrite
    public void onUpdateWalkingPlayer()
    {
        EventMotionUpdate.Pre event = new EventMotionUpdate.Pre(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround, this.isSprinting(), this.isSneaking());
        if (MinecraftForge.EVENT_BUS.post(event)) {
            return;
        }
        boolean flag = event.sprinting;
        if (flag != this.serverSprintState)
        {
            if (flag)
            {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction((EntityPlayerSP)((Object)this), C0BPacketEntityAction.Action.START_SPRINTING));
            }
            else
            {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction((EntityPlayerSP)((Object)this), C0BPacketEntityAction.Action.STOP_SPRINTING));
            }

            this.serverSprintState = flag;
        }

        boolean flag1 = event.sneaking;

        if (flag1 != this.serverSneakState)
        {
            if (flag1)
            {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction((EntityPlayerSP)((Object)this), C0BPacketEntityAction.Action.START_SNEAKING));
            }
            else
            {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction((EntityPlayerSP)((Object)this), C0BPacketEntityAction.Action.STOP_SNEAKING));
            }

            this.serverSneakState = flag1;
        }

        if (this.isCurrentViewEntity())
        {
            double d0 = event.x - this.lastReportedPosX;
            double d1 = event.y - this.lastReportedPosY;
            double d2 = event.z - this.lastReportedPosZ;
            double d3 = (double)(event.yaw - this.lastReportedYaw);
            double d4 = (double)(event.pitch - this.lastReportedPitch);
            boolean flag2 = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || this.positionUpdateTicks >= 20;
            boolean flag3 = d3 != 0.0D || d4 != 0.0D;

            if (this.ridingEntity == null)
            {
                if (flag2 && flag3)
                {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(event.x, event.y, event.z, event.yaw, event.pitch, event.onGround));
                }
                else if (flag2)
                {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(event.x, event.y, event.z, event.onGround));
                }
                else if (flag3)
                {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(event.yaw, event.pitch, event.onGround));
                }
                else
                {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer(event.onGround));
                }
            }
            else
            {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0D, this.motionZ, event.yaw, event.pitch, event.onGround));
                flag2 = false;
            }

            ++this.positionUpdateTicks;

            if (flag2)
            {
                this.lastReportedPosX = event.x;
                this.lastReportedPosY = event.y;
                this.lastReportedPosZ = event.z;
                this.positionUpdateTicks = 0;
            }

            if (flag3)
            {
                this.lastReportedYaw = event.yaw;
                this.lastReportedPitch = event.pitch;
            }
        }
        MinecraftForge.EVENT_BUS.post(new EventMotionUpdate.Post(event));
    }
}

