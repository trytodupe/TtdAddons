package com.trytodupe.ttdaddons.Features;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class Hitboxes {

    public static void toggle() {
        ConfigHandler.hitboxes = !ConfigHandler.hitboxes;
        if (ConfigHandler.hitboxes) ChatLib.chat("Hitboxes &aenabled");
        else ChatLib.chat("Hitboxes &cdisabled");
    }

    public static void setExpand(String input) {
        try {
            double d = Double.parseDouble(input);
            if (d >= 0.1D && d <= 1.0D) ConfigHandler.hitboxesExpand = d;
            else throw new Exception();
            ChatLib.chat("Hitboxes expand set to &b" + ConfigHandler.hitboxesExpand);
        }
        catch (Exception e) {
            ChatLib.chat("Please enter a valid number between 0.1 to 1.");
        }
    }

    public static void toggleWeaponsOnly() {
        ConfigHandler.hitboxesWeaponsOnly = !ConfigHandler.hitboxesWeaponsOnly;
        if (ConfigHandler.hitboxesWeaponsOnly) ChatLib.chat("Hitboxes Weapons Only &aenabled");
        else ChatLib.chat("Hitboxes Weapons Only &cdisabled");
    }

    public static double getExpand() {
        if (mc.thePlayer.getHeldItem() == null) return 0.0D;
        if (!(mc.thePlayer.getHeldItem().getItem().getUnlocalizedName().contains("shovelDiamond") ||
            mc.thePlayer.getHeldItem().getItem().getUnlocalizedName().contains("sword"))) return 0.0D;
        else return ConfigHandler.hitboxes ? ConfigHandler.hitboxesExpand: 0.0D;
    }
}
