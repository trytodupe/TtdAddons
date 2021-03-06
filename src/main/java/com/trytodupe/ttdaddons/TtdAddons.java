package com.trytodupe.ttdaddons;

import com.mojang.authlib.GameProfile;
import com.trytodupe.ttdaddons.config.ConfigHandler;
import com.trytodupe.ttdaddons.features.AutoReadyUp;
import com.trytodupe.ttdaddons.features.TunnelMiner;
import com.trytodupe.ttdaddons.features.ChestFiller;
import com.trytodupe.ttdaddons.features.InvMove;
import com.trytodupe.ttdaddons.features.LegitSpeed;
import com.trytodupe.ttdaddons.features.NoRotate;
import com.trytodupe.ttdaddons.features.NoSlow;
import com.trytodupe.ttdaddons.features.HClip;
import com.trytodupe.ttdaddons.features.RejoinGame;
import com.trytodupe.ttdaddons.features.Nuker;
import com.trytodupe.ttdaddons.objects.KeyBind;
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
    public static final String VERSION = "0.1.10";
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
        MinecraftForge.EVENT_BUS.register(new InvMove());
        MinecraftForge.EVENT_BUS.register(new Nuker());
        MinecraftForge.EVENT_BUS.register(new RejoinGame());
        MinecraftForge.EVENT_BUS.register(new NoRotate());
        MinecraftForge.EVENT_BUS.register(new TunnelMiner());
        // MinecraftForge.EVENT_BUS.register(new TtdAddons());

        ClientCommandHandler.instance.registerCommand(new Commands());
        ClientCommandHandler.instance.registerCommand(new HClip());

        for (KeyBind keyBind : KeyBind.keyBinds) {
            ClientRegistry.registerKeyBinding(keyBind.mcKeyBinding());
        }
    }

/*    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        if (!isDebug()) return;
        System.out.println(event.target.getDisplayName().getFormattedText() + " " + GhostHand.shouldHitThrough(event.target));
    }*/
}
