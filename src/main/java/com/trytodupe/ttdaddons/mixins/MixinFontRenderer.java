package com.trytodupe.ttdaddons.mixins;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

@Mixin({ FontRenderer.class })
public abstract class MixinFontRenderer {

    @ModifyArg(method = { "renderString" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderStringAtPos(Ljava/lang/String;Z)V"))
    public String changeString(final String in) {
        if (!ConfigHandler.deobfuscator) return in;

        String title;
        if (mc.theWorld == null || mc.theWorld.getScoreboard() == null || mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1) == null) title = " ";
        else title = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1).getDisplayName();
        if (!StringUtils.stripControlCodes(title).contains("MEGA WALLS")) return in;

        return in.replace("§k", "");
    }
/*    @ModifyVariable(method = { "getStringWidth" }, at = @At("HEAD"))
    public String modifyStringWidth(final String in) {
        if (!ConfigHandler.deobfuscator) return in;
        return in.replace("§k", "");
    }*/
}

