package com.trytodupe.ttdaddons.Mixins;

import com.trytodupe.ttdaddons.Features.GhostHand;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

import static com.trytodupe.ttdaddons.Features.GhostHand.isTeam;

@Mixin(value={EntityRenderer.class})
public class MixinEntityRenderer2 {

    @Inject(method = {"getMouseOver"}, at = {@At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 0)}, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void getMouseOver(float partialTicks, CallbackInfo ci, Entity entity, double d0, double d1, Vec3 vec3, boolean flag, boolean b, Vec3 vec31, Vec3 vec32, Vec3 vec33, float f, List<Entity> list, double d2, int j) {
        removeEntities(list);
    }

    @Inject(method = {"getMouseOver"}, at = {@At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 0)}, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void getMouseOver(float partialTicks, CallbackInfo ci, Entity entity, double d0, double d1, Vec3 vec3, boolean flag, int i, Vec3 vec31, Vec3 vec32, Vec3 vec33, float f, List<Entity> list, double d2, int j) {
        removeEntities(list);
    }

    private void removeEntities(List<Entity> list) {
        if (GhostHand.isToggled()) list.removeIf(GhostHand::isTeam);
    }

}
