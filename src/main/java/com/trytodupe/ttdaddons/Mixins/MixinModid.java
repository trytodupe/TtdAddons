package com.trytodupe.ttdaddons.Mixins;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.network.handshake.FMLHandshakeMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin({FMLHandshakeMessage.ModList.class})
public abstract class MixinModid {

    @Shadow(remap = false)
    private Map<String, String> modTags;

    @Inject(method = "<init>(Ljava/util/List;)V", at = @At("RETURN"), remap = false)
    private void removeMod(List<ModContainer> modList, CallbackInfo ci) {
        if (Minecraft.getMinecraft().isSingleplayer()) return;
        this.modTags.remove("ttdaddons");
        if (!ConfigHandler.modLess) return;
        this.modTags.entrySet().removeIf(mod -> (!((String)mod.getKey()).equalsIgnoreCase("fml") && !((String)mod.getKey()).equalsIgnoreCase("forge") && !((String)mod.getKey()).equalsIgnoreCase("mcp")));
    }
}
