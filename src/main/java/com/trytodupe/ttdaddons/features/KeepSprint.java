package com.trytodupe.ttdaddons.features;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;

public class KeepSprint {
    public static void toggle() {
        ConfigHandler.keepSprint = !ConfigHandler.keepSprint;
        if (ConfigHandler.keepSprint) ChatLib.chat("Keep Sprint &aenabled");
        else ChatLib.chat("Keep Sprint &cdisabled");
    }
}
