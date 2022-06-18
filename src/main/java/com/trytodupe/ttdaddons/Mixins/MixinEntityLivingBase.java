package com.trytodupe.ttdaddons.Mixins;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.Potion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityLivingBase.class})
public abstract class MixinEntityLivingBase extends MixinEntity {
    @Shadow
    public abstract IAttributeInstance getEntityAttribute(IAttribute paramIAttribute);

    @Shadow
    public abstract boolean isOnLadder();

    @Shadow
    public abstract boolean isPotionActive(Potion paramPotion);

    @Shadow
    public abstract void setLastAttacker(Entity paramEntity);

    @Shadow
    private int jumpTicks;

    @Shadow
    public float prevRenderYawOffset;

    @Shadow
    public float renderYawOffset;

    @Shadow
    public float prevRotationYawHead;

    @Shadow
    public float rotationYawHead;

    @Inject(method = {"onLivingUpdate"}, at = @At(value = "HEAD"))
    public void onLivingUpdate(CallbackInfo ci) {
        if (ConfigHandler.noJumpDelay) jumpTicks = 0;
    }

}
