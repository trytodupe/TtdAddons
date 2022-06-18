package com.trytodupe.ttdaddons.Mixins;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.Features.HeadRotation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

@Mixin({RendererLivingEntity.class})
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> extends Render<T> {

    protected MixinRendererLivingEntity(RenderManager renderManager) {
        super(renderManager);
    }

    @Shadow
    protected abstract float interpolateRotation(float paramFloat1, float paramFloat2, float paramFloat3);
/*
    @Redirect(method = {"doRender"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/Entity/RendererLivingEntity;prevRenderYawOffset:F"))
    public float prevRenderYawOffset(T instance) {
        if (ConfigHandler.headRotation && instance == mc.thePlayer && HeadRotation.shouldRotate()) return HeadRotation.prevServerYaw;
        else return instance.prevRenderYawOffset;
    }

    @Inject(method = {"doRender"}, at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;interpolateRotation(FFF)F", ordinal = 1))
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        float f = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
        float f1 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
        float f7 = ((EntityLivingBase)entity).prevRotationPitch + (((EntityLivingBase)entity).rotationPitch  - ((EntityLivingBase)entity).prevRotationPitch) * partialTicks;
        if (ConfigHandler.headRotation && entity == mc.thePlayer && HeadRotation.shouldRotate()) {
            f = interpolateRotation(HeadRotation.prevServerYaw, HeadRotation.serverYaw, partialTicks);
            f1 = interpolateRotation(HeadRotation.prevServerYaw, HeadRotation.serverYaw, partialTicks);
            f7 = HeadRotation.prevServerPitch + (HeadRotation.serverPitch - HeadRotation.prevServerPitch) * partialTicks;
        }
    }
*/

    @Redirect(method = {"doRender"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;interpolateRotation(FFF)F", ordinal = 0))
    public float render1(T instance, float prevRenderYawOffset, float renderYawOffset, float partialTicks) {
        if (ConfigHandler.headRotation && instance == mc.thePlayer && HeadRotation.shouldRotate())
            return interpolateRotation(HeadRotation.prevServerYaw, HeadRotation.serverYaw, partialTicks);
        else return this.interpolateRotation(instance.prevRenderYawOffset, instance.renderYawOffset, partialTicks);
    }

    @Redirect(method = {"doRender"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;interpolateRotation(FFF)F", ordinal = 1))
    public float render2(T instance, float prevRenderYawOffset, float renderYawOffset, float partialTicks) {
        if (ConfigHandler.headRotation && instance == mc.thePlayer && HeadRotation.shouldRotate())
            return interpolateRotation(HeadRotation.prevServerYaw, HeadRotation.serverYaw, partialTicks);
        else return this.interpolateRotation(instance.prevRotationYawHead, instance.rotationYawHead, partialTicks);
    }

    @Redirect(method = {"doRender"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/Entity;rotationPitchF", ordinal = 3, fstore = 0))
    public float render3(T instance, float prevRenderYawOffset, float renderYawOffset, float partialTicks) {
        if (ConfigHandler.headRotation && instance == mc.thePlayer && HeadRotation.shouldRotate())
            return HeadRotation.prevServerPitch + (HeadRotation.serverPitch - HeadRotation.prevServerPitch) * partialTicks;
        else return instance.prevRotationPitch + (instance.rotationPitch - instance.prevRotationPitch) * partialTicks;
    }
}
