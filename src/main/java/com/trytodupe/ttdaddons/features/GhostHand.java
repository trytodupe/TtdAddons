package com.trytodupe.ttdaddons.features;

import com.trytodupe.ttdaddons.TtdAddons;
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
        String title, name1 = e.getDisplayName().getFormattedText(), name2 = mc.thePlayer.getDisplayName().getFormattedText();

        if (name1.startsWith("§r§6[§2S§6] ")) name1 = name1.replace("§r§6[§2S§6] ", "");
        if (name2.startsWith("§r§6[§2S§6] ")) name2 = name2.replace("§r§6[§2S§6] ", "");

        if (mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1) == null) title = " ";
        else title = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1).getDisplayName();

        if (name1.charAt(2) == '§')
            color1 = name1.charAt(3);
        else if (name1.charAt(0) == '§' && name1.charAt(1) != 'r')
            color1 = name1.charAt(1);

        if (name2.charAt(2) == '§')
            color2 = name2.charAt(3);
        else if (name2.charAt(0) == '§' && name2.charAt(2) != '§' && name2.charAt(1) == 'r' && title.charAt(0) == '§')
            color2 = title.charAt(1);

/*
        if (TtdAddons.isDebug()) {
            System.out.println(e.getDisplayName().getFormattedText());
            System.out.println("0:" + e.getDisplayName().getFormattedText().charAt(0) + " 1:" + e.getDisplayName().getFormattedText().charAt(1) + " 2:" + e.getDisplayName().getFormattedText().charAt(2) + " 3:" + e.getDisplayName().getFormattedText().charAt(3));
            System.out.println(color1 + "§§§" + color2);
            System.out.println("0:" + (e.getDisplayName().getFormattedText().charAt(0) == '§') + " 1:" + (e.getDisplayName().getFormattedText().charAt(1) == 'r') + " 2:" + (e.getDisplayName().getFormattedText().charAt(2) == '§'));
        }
*/

        return color1 != ' ' && color2 != ' ' && color1 == color2;
    }
}
