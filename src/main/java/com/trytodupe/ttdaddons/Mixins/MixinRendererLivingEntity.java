package com.trytodupe.ttdaddons.Mixins;


import com.trytodupe.ttdaddons.Config.ConfigHandler;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
/*

@Mixin({RendererLivingEntity.class})
public class MixinRendererLivingEntity {
    @Redirect(method = {"doRender*"}, at = @At(value="INVOKE", target ="Lnet/minecraft/client/renderer/entity/RendererLivingEntity;interpolateRotation(FFF)F"))
    protected float interpolateRotation(float par1, float par2, float par3) {

        return par1;
    }
}
*/
