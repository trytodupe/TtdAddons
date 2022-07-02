package com.trytodupe.ttdaddons.features;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;

public class CameraClip {

    public static void toggle() {
        ConfigHandler.cameraClip = !ConfigHandler.cameraClip;
        if (ConfigHandler.cameraClip) ChatLib.chat("Camera Clip &aenabled");
        else ChatLib.chat("Camera Clip &cdisabled");
    }

    public static void setDistance(String input) {
        try {
            double d = Double.parseDouble(input);
            if (d > 0D) ConfigHandler.cameraClipDistance = d;
            else throw new Exception();
            ChatLib.chat("Camera Clip distance set to &b" + ConfigHandler.cameraClipDistance);
        }
        catch (Exception e) {
            ChatLib.chat("Please enter a valid number grater than 0.");
        }
    }
}
