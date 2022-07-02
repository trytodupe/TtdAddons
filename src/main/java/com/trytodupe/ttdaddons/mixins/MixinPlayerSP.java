package com.trytodupe.ttdaddons.mixins;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import net.minecraft.client.entity.EntityPlayerSP;
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
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
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

    public boolean isEntityInsideOpaqueBlock() {
        return false;
    }

    //todo: this mixin might cause some issues
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

                i = i + EnchantmentHelper.getKnockbackModifier((EntityLivingBase)mc.thePlayer);

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
                    int j = EnchantmentHelper.getFireAspectModifier((EntityLivingBase)mc.thePlayer);

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

                        EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)mc.thePlayer, targetEntity);
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
}

