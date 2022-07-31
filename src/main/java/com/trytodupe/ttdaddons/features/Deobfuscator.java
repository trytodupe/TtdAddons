package com.trytodupe.ttdaddons.features;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;


public class Deobfuscator {

    public static void toggle() {
        ConfigHandler.deobfuscator = !ConfigHandler.deobfuscator;
        if (ConfigHandler.deobfuscator) ChatLib.chat("Deobfuscator &aenabled");
        else ChatLib.chat("Deobfuscator &cdisabled");
    }
}
