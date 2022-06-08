package com.trytodupe.ttdaddons.Features;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;

public class Reach {

    public static void toggle() {
        ConfigHandler.reach = !ConfigHandler.reach;
        if (ConfigHandler.reach) ChatLib.chat("Reach &aenabled");
        else ChatLib.chat("Reach &cdisabled");
    }

    public static void setReach(String input) {
        try {
            double d = Double.parseDouble(input);
            if (d >= 3D && d <= 6D) ConfigHandler.reachRange = d;
            else throw new Exception();
            ChatLib.chat("Reach set to &b" + ConfigHandler.reachRange);
        }
        catch (Exception e) {
            ChatLib.chat("Please enter a valid number between 3 to 6.");
        }
    }
}
