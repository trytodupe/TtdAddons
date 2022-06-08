package com.trytodupe.ttdaddons.Features;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;
import net.minecraft.client.Minecraft;

import net.minecraft.entity.Entity;

public class GhostHand {

    public static final Minecraft mc = Minecraft.getMinecraft();

    public static void toggle() {
        ConfigHandler.ghostHand = !ConfigHandler.ghostHand;
        if (ConfigHandler.ghostHand) ChatLib.chat("Ghost Hand &aenabled");
        else ChatLib.chat("Ghost Hand &cdisabled");
    }

    public static void togglePickaxe() {
        ConfigHandler.ghostHandPickaxe = !ConfigHandler.ghostHandPickaxe;
        if (ConfigHandler.ghostHandPickaxe) ChatLib.chat("Ghost Hand pickaxe &aenabled");
        else ChatLib.chat("Ghost Hand pickaxe &cdisabled");
    }

    public static boolean isTeam(Entity e) {
        Entity e2 = mc.thePlayer;
        if (ConfigHandler.ghostHandPickaxe && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem().getUnlocalizedName().contains("pickaxe"))
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
