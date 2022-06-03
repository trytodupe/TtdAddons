package com.trytodupe.ttdaddons;

import com.trytodupe.ttdaddons.Features.ChestFiller;
import com.trytodupe.ttdaddons.Commands;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = TtdAddons.MODID, version = TtdAddons.VERSION)
public class TtdAddons
{
    public static final String MODID = "ttdaddons";
    public static final String VERSION = "0.0.2";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ChestFiller());
        ClientCommandHandler.instance.registerCommand(new Commands());
    }
}
