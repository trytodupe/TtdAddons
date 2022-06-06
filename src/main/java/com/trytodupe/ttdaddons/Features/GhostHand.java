package com.trytodupe.ttdaddons.Features;

import com.trytodupe.ttdaddons.utils.ChatLib;
import net.minecraft.client.Minecraft;

import net.minecraft.entity.Entity;

public class GhostHand {

    public static final Minecraft mc = Minecraft.getMinecraft();

    private static boolean enabled = true;

    public static void toggle() {
        enabled = !enabled;
        if (enabled) ChatLib.chat("Ghost hand &aenabled");
        else ChatLib.chat("Ghost hand &cdisabled");
    }

    public static boolean isToggled() {
        return enabled;
    }

    public static boolean isTeam(Entity e) {
        Entity e2 = mc.thePlayer;
        //add config
        if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem().getUnlocalizedName().contains("pickaxe"))
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
