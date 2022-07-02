package com.trytodupe.ttdaddons.mixins;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import com.trytodupe.ttdaddons.features.HeadRotation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

@Mixin({RendererLivingEntity.class})
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> extends Render<T> {

    protected MixinRendererLivingEntity(RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(method = {"doRender"}, at = @At("HEAD"))
    public void injectData(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (ConfigHandler.headRotation && entity == mc.thePlayer && HeadRotation.shouldRotate()) {
            entity.prevRenderYawOffset = HeadRotation.prevServerYaw;
            entity.prevRotationYawHead = HeadRotation.prevServerYaw;
            entity.renderYawOffset = HeadRotation.serverYaw;
            entity.rotationYawHead = HeadRotation.serverYaw;
            HeadRotation.prevClientPitch = entity.prevRotationPitch;
            HeadRotation.clientPitch = entity.rotationPitch;
            entity.prevRotationPitch = HeadRotation.prevServerPitch;
            entity.rotationPitch = HeadRotation.serverPitch;
        }
    }

    @Inject(method = {"doRender"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;renderLivingAt(Lnet/minecraft/entity/EntityLivingBase;DDD)V"))
    public void uninjectData(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (ConfigHandler.headRotation && entity == mc.thePlayer && HeadRotation.shouldRotate()) {
            entity.prevRotationPitch = HeadRotation.prevClientPitch;
            entity.rotationPitch = HeadRotation.clientPitch;
        }
    }
}
