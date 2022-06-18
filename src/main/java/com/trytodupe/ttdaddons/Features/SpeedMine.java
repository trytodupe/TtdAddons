package com.trytodupe.ttdaddons.Features;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;

public class SpeedMine {

    public static void toggle() {
        ConfigHandler.speedMine = !ConfigHandler.speedMine;
        if (ConfigHandler.speedMine) ChatLib.chat("Speed Mine &aenabled");
        else ChatLib.chat("Speed Mine &cdisabled");
    }

    public static void setSpeed(String input) {
        try {
            double d = Double.parseDouble(input);
            if (d >= 1D && d <= 1.6D) {
                ConfigHandler.speedMineSpeed = d;
                ChatLib.chat("Speed Mine speed set to &b" + ConfigHandler.speedMineSpeed);
            }
            else if (d > 0d && d <= 0.9d) {
                ConfigHandler.speedMineBlockBreak = d;
                ChatLib.chat("Speed Mine block break at set to &b" + ConfigHandler.speedMineBlockBreak);
            }
            else throw new Exception();
        }
        catch (Exception e) {
            ChatLib.chat("Please enter a boolean or a valid number between 1 to 1.6 or 0 to 0.9.");
        }
    }
}
