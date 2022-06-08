package com.trytodupe.ttdaddons.Mixins;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.Features.CameraClip;
import com.trytodupe.ttdaddons.Features.GhostHand;
import com.trytodupe.ttdaddons.Features.Hitboxes;
import com.trytodupe.ttdaddons.Features.Reach;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(value={EntityRenderer.class})
public class MixinEntityRenderer {
    @Shadow
    private float thirdPersonDistanceTemp;
    @Shadow
    private float thirdPersonDistance;

    private void removeEntities(List<Entity> list) {
        if (ConfigHandler.ghostHand) list.removeIf(GhostHand::isTeam);
    }

    @Redirect(method = {"orientCamera"}, at = @At(value="FIELD", target="Lnet/minecraft/client/renderer/EntityRenderer;thirdPersonDistanceTemp:F"))
    public float thirdPersonDistanceTemp(EntityRenderer instance) {
        return ConfigHandler.cameraClip ? (float) ConfigHandler.cameraClipDistance : this.thirdPersonDistanceTemp;
    }

    @Redirect(method = {"orientCamera"}, at = @At(value="FIELD", target="Lnet/minecraft/client/renderer/EntityRenderer;thirdPersonDistance:F"))
    public float thirdPersonDistance(EntityRenderer instance) {
        return ConfigHandler.cameraClip ? (float) ConfigHandler.cameraClipDistance : this.thirdPersonDistance;
    }

    @Redirect(method = {"orientCamera"}, at = @At(value="INVOKE", target="Lnet/minecraft/util/Vec3;distanceTo(Lnet/minecraft/util/Vec3;)D"))
    public double onCamera(Vec3 instance, Vec3 vec) {
        return ConfigHandler.cameraClip ? ConfigHandler.cameraClipDistance : instance.distanceTo(vec);
    }

    @Inject(method = {"getMouseOver"}, at = {@At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 0)}, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void getMouseOver(float partialTicks, CallbackInfo ci, Entity entity, double d0, double d1, Vec3 vec3, boolean flag, int i, Vec3 vec31, Vec3 vec32, Vec3 vec33, float f, List<Entity> list, double d2, int j) {
        removeEntities(list);
    }


    @Redirect(method = {"getMouseOver"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Vec3;distanceTo(Lnet/minecraft/util/Vec3;)D", ordinal = 2))
    private double distanceTo(Vec3 instance, Vec3 vec) {
        return (ConfigHandler.reach && instance.distanceTo(vec) + (
                ConfigHandler.hitboxes ? ConfigHandler.hitboxesExpand : 0.0D) <= ConfigHandler.reachRange) ? 2.9D : (instance
                .distanceTo(vec) + (ConfigHandler.hitboxes ? ConfigHandler.hitboxesExpand: 0.0D));
    }

    @Redirect(method = {"getMouseOver"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getCollisionBorderSize()F"))
    private float getCollisionBorderSize(Entity instance) {
        return (ConfigHandler.hitboxes && (instance instanceof EntityWither || instance instanceof EntityPlayer)) ? ((float)ConfigHandler.hitboxesExpand + 0.1F) : instance.getCollisionBorderSize();
    }
}
