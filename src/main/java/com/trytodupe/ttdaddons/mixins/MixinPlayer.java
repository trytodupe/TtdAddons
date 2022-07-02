package com.trytodupe.ttdaddons.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({EntityPlayer.class})
public abstract class MixinPlayer extends MixinEntityLivingBase{
    @Shadow
    public abstract void attackTargetEntityWithCurrentItem(Entity paramEntity);

    @Shadow
    public boolean isEntityInsideOpaqueBlock() {
        return false;
    }

    @Shadow
    public abstract ItemStack getHeldItem();

    @Shadow
    public abstract void triggerAchievement(StatBase paramStatBase);

    @Shadow
    public abstract ItemStack getCurrentEquippedItem();

    @Shadow
    public abstract void destroyCurrentEquippedItem();

    @Shadow
    public abstract void addExhaustion(float paramFloat);
}
