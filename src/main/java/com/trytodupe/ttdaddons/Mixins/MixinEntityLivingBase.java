package com.trytodupe.ttdaddons.Mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.Potion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({EntityLivingBase.class})
public abstract class MixinEntityLivingBase extends MixinEntity{
    @Shadow
    public abstract IAttributeInstance getEntityAttribute(IAttribute paramIAttribute);

    @Shadow
    public abstract boolean isOnLadder();

    @Shadow
    public abstract boolean isPotionActive(Potion paramPotion);

    @Shadow
    public abstract void setLastAttacker(Entity paramEntity);
}
