package com.trytodupe.ttdaddons.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatLib {

    public static void chat(Object message) {
        if (message instanceof String) {
            for (String s : ((String) message).split("\n")) {
                s = "&f[&dTTD&f]&r " + s;
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText(s.replaceAll("&", "§"))
                );
            }
        } else {
            message = ("&f[&dTTD&f]&r " + message).replaceAll("&", "§");
            Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText(message.toString()));
        }
    }
}