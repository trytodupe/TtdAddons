package com.trytodupe.ttdaddons.features;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;

public class ModLess {

    public static void toggle() {
        ConfigHandler.modLess = !ConfigHandler.modLess;
        if (ConfigHandler.modLess) ChatLib.chat("Mod Less &aenabled");
        else ChatLib.chat("Mod Less &cdisabled");
    }
}
