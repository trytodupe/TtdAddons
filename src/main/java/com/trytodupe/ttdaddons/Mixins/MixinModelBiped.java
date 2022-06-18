package com.trytodupe.ttdaddons.Mixins;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.Features.HeadRotation;
import com.trytodupe.ttdaddons.utils.ReflectionUtils;
import com.trytodupe.ttdaddons.utils.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

@Mixin({ModelBiped.class})
public class MixinModelBiped {

    @Shadow
    public ModelRenderer bipedHead;

    @Inject(method = {"setRotationAngles"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelBiped;swingProgress:F")})
    private void setRotationAngles(float p_setRotationAngles_1_, float p_setRotationAngles_2_, float p_setRotationAngles_3_, float p_setRotationAngles_4_, float p_setRotationAngles_5_, float p_setRotationAngles_6_, Entity p_setRotationAngles_7_, CallbackInfo callbackInfo) {
        if (ConfigHandler.headRotation)//反
            return;
        if (HeadRotation.shouldRotate() && p_setRotationAngles_7_ != null && p_setRotationAngles_7_.equals(mc.thePlayer)) {
            Timer timer = (Timer)ReflectionUtils.getFieldByName(Minecraft.class, "field_71428_T", mc);
            if (timer != null){
                this.bipedHead.rotateAngleX = (RotationUtils.lastReportedPitch + (((PlayerSPAccessor)p_setRotationAngles_7_).getLastReportedPitch() - RotationUtils.lastReportedPitch) * (((MinecraftAccessor)mc).getTimer()).renderPartialTicks) / 57.295776F;
            }


        }
    }
}
