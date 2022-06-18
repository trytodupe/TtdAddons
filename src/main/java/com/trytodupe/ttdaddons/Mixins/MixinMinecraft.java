package com.trytodupe.ttdaddons.Mixins;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.Features.HeadRotation;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

@Mixin(value = {Minecraft.class})
public class MixinMinecraft {

    @Shadow
    private Entity renderViewEntity;

    @Inject(method = {"getRenderViewEntity"}, at = {@At("HEAD")})
    public void getRenderViewEntity(CallbackInfoReturnable<Entity> cir) {//反
        if (!ConfigHandler.headRotation && HeadRotation.shouldRotate() && this.renderViewEntity != null && this.renderViewEntity == mc.thePlayer) {
            ((EntityLivingBase)this.renderViewEntity).rotationYawHead = ((PlayerSPAccessor)this.renderViewEntity).getLastReportedYaw();
            ((EntityLivingBase)this.renderViewEntity).prevRenderYawOffset = ((PlayerSPAccessor)this.renderViewEntity).getLastReportedYaw();
        }
    }
}
