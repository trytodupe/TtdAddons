package com.trytodupe.ttdaddons;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.Features.AutoReadyUp;
import com.trytodupe.ttdaddons.Features.ChestFiller;
import com.trytodupe.ttdaddons.Features.LegitSpeed;
import com.trytodupe.ttdaddons.Features.NoSlow;
import com.trytodupe.ttdaddons.Objects.KeyBind;
import com.trytodupe.ttdaddons.utils.ChatLib;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = TtdAddons.MODID, version = TtdAddons.VERSION)
public class TtdAddons
{
    public static final String MODID = "ttdaddons";
    public static final String VERSION = "0.0.12";
    private static boolean debug = false;
    public static File configurationFile;
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static void toggleDebug() {
        debug = !debug;
        ChatLib.chat("Debug: " + debug);
    }

    public static boolean isDebug() {
        return debug;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configurationFile = event.getSuggestedConfigurationFile();
        ConfigHandler.preinit(configurationFile);
    }

    @EventHandler
    public void Init(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ChestFiller());
        MinecraftForge.EVENT_BUS.register(new LegitSpeed());
        MinecraftForge.EVENT_BUS.register(new NoSlow());
        MinecraftForge.EVENT_BUS.register(new KeyBinds());
        MinecraftForge.EVENT_BUS.register(new AutoReadyUp());

        ClientCommandHandler.instance.registerCommand(new Commands());

        for (KeyBind keyBind : KeyBind.keyBinds) {
            ClientRegistry.registerKeyBinding(keyBind.mcKeyBinding());
        }
    }
}
