package com.trytodupe.ttdaddons.Features;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;

import net.minecraft.entity.Entity;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class GhostHand {

    public static void toggle() {
        ConfigHandler.ghostHand = !ConfigHandler.ghostHand;
        if (ConfigHandler.ghostHand) ChatLib.chat("Ghost Hand &aenabled");
        else ChatLib.chat("Ghost Hand &cdisabled");
    }

    public static void toggleTools() {
        ConfigHandler.ghostHandTools = !ConfigHandler.ghostHandTools;
        if (ConfigHandler.ghostHandTools) ChatLib.chat("Ghost Hand tools &aenabled");
        else ChatLib.chat("Ghost Hand tools &cdisabled");
    }

    public static boolean shouldHitThrough(Entity e) {
        Entity e2 = mc.thePlayer;
        if (ConfigHandler.ghostHandTools && mc.thePlayer.getHeldItem() != null &&
                (mc.thePlayer.getHeldItem().getItem().getUnlocalizedName().contains("pickaxe")
                || mc.thePlayer.getHeldItem().getItem().getUnlocalizedName().contains("hatchet") //who tf made axe called hatchet
                || mc.thePlayer.getHeldItem().getItem().getUnlocalizedName().contains("shovel")
                || mc.thePlayer.getHeldItem().getItem().getUnlocalizedName().contains("hoe")))
            return true;
        if (e.getDisplayName().getUnformattedText().length() < 4)
            return false;
        if (e.getDisplayName().getFormattedText().charAt(2) == '§' && e2.getDisplayName().getFormattedText().charAt(2) == '§')
            return (e.getDisplayName().getFormattedText().charAt(3) == e2.getDisplayName().getFormattedText().charAt(3));
        else if (e.getDisplayName().getFormattedText().charAt(0) == '§' && e2.getDisplayName().getFormattedText().charAt(2) == '§' && e.getDisplayName().getFormattedText().charAt(1) != 'r')
            return (e.getDisplayName().getFormattedText().charAt(1) == e2.getDisplayName().getFormattedText().charAt(3));
        return false;
    }
}
