package com.trytodupe.ttdaddons.Mixins;

import com.trytodupe.ttdaddons.Features.CameraClip;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={EntityRenderer.class})
public class EntityRendererMixin {
    @Shadow
    private float thirdPersonDistanceTemp;
    @Shadow
    private float thirdPersonDistance;

    @Redirect(method={"orientCamera"}, at=@At(value="FIELD", target="Lnet/minecraft/client/renderer/EntityRenderer;thirdPersonDistanceTemp:F"))
    public float thirdPersonDistanceTemp(EntityRenderer instance) {
        return CameraClip.isToggled() ? (float) CameraClip.getDistance() : this.thirdPersonDistanceTemp;
    }

    @Redirect(method={"orientCamera"}, at=@At(value="FIELD", target="Lnet/minecraft/client/renderer/EntityRenderer;thirdPersonDistance:F"))
    public float thirdPersonDistance(EntityRenderer instance) {
        return CameraClip.isToggled() ? (float) CameraClip.getDistance() : this.thirdPersonDistance;
    }

    @Redirect(method={"orientCamera"}, at=@At(value="INVOKE", target="Lnet/minecraft/util/Vec3;distanceTo(Lnet/minecraft/util/Vec3;)D"))
    public double onCamera(Vec3 instance, Vec3 vec) {
        return CameraClip.isToggled() ? CameraClip.getDistance() : instance.distanceTo(vec);
    }
}
