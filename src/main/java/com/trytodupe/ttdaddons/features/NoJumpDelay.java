package com.trytodupe.ttdaddons.features;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;

public class NoJumpDelay {
    public static void toggle() {
        ConfigHandler.noJumpDelay = !ConfigHandler.noJumpDelay;
        if (ConfigHandler.noJumpDelay) ChatLib.chat("No Jump Delay &aenabled");
        else ChatLib.chat("No Jump Delay &cdisabled");
    }
}
