package com.trytodupe.ttdaddons.Features;

import com.trytodupe.ttdaddons.utils.ChatLib;

public class CameraClip {
    private static boolean enabled = true;

    private static double distance = 4.0D;

    public static void toggle() {
        enabled = !enabled;
        if (enabled) ChatLib.chat("Camera Clip &aenabled");
        else ChatLib.chat("Camera Clip &cdisabled");
    }

    public static void setDistance(double input) {
        distance = input;
        ChatLib.chat("Camera Clip distance set to &b " + distance);
    }

    public static boolean isToggled() {
        return enabled;
    }

    public static double getDistance() {
        return distance;
    }
}
