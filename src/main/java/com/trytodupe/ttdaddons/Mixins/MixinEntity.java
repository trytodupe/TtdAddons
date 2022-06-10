package com.trytodupe.ttdaddons.Mixins;


import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({Entity.class})
public abstract class MixinEntity {
    @Shadow
    public abstract boolean isEntityInsideOpaqueBlock();

    @Shadow
    public abstract boolean isSprinting();

    @Shadow
    public float fallDistance;

    @Shadow
    public boolean onGround;

    @Shadow
    public abstract boolean isInWater();

    @Shadow
    public Entity ridingEntity;

    @Shadow
    public float rotationYaw;

    @Shadow
    public double motionX;

    @Shadow
    public double motionZ;
}