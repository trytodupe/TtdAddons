package com.trytodupe.ttdaddons.Features;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;

public class ModLess {

    public static void toggle() {
        ConfigHandler.modLess = !ConfigHandler.modLess;
        if (ConfigHandler.modLess) ChatLib.chat("Mod Less &aenabled");
        else ChatLib.chat("Mod Less &cdisabled");
    }
}
