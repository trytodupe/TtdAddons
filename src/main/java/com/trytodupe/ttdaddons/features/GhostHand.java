package com.trytodupe.ttdaddons.features;

import com.trytodupe.ttdaddons.config.ConfigHandler;
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
                || mc.thePlayer.getHeldItem().getItem().getUnlocalizedName().contains("hoe")) &&
                !mc.thePlayer.getHeldItem().getItem().getUnlocalizedName().contains("shovelDiamond"))
            return true;
        if (e.getDisplayName().getUnformattedText().length() < 4)
            return false;

        char color1 = ' ', color2 = ' ';
        String title;
        if (mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1) == null) title = " ";
        else title = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1).getDisplayName();

        if (e.getDisplayName().getFormattedText().charAt(2) == '§')
            color1 = e.getDisplayName().getFormattedText().charAt(3);
        else if (e.getDisplayName().getFormattedText().charAt(0) == '§' && e.getDisplayName().getFormattedText().charAt(1) != 'r')
            color1 = e.getDisplayName().getFormattedText().charAt(1);

        if (e2.getDisplayName().getFormattedText().charAt(2) == '§')
            color2 = e2.getDisplayName().getFormattedText().charAt(3);
        else if (e2.getDisplayName().getFormattedText().charAt(0) == '§' && e2.getDisplayName().getFormattedText().charAt(2) != '§' && e2.getDisplayName().getFormattedText().charAt(1) == 'r' && title.charAt(0) == '§')
            color2 = title.charAt(1);

        return color1 != ' ' && color2 != ' ' && color1 == color2;
    }
}
