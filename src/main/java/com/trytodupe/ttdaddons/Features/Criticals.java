package com.trytodupe.ttdaddons.Features;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;
import net.minecraft.client.Minecraft;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class Criticals {
    private int groundTicks;

    public static void toggle() {
        ConfigHandler.criticals = !ConfigHandler.criticals;
        if (ConfigHandler.criticals) {
            ChatLib.chat("Criticals &aenabled");
            if (mc.thePlayer.onGround) mc.thePlayer.jump();
        }
        else ChatLib.chat("Criticals &cdisabled");
    }
/*
    @EventTarget
    void onPacket(EventPacket e) {
        if (mode.getValue().equals(CritMode.NoGround)) {
            if (e.getPacket() instanceof C03PacketPlayer) {
                ((C03PacketPlayer) e.getPacket()).onGround = false;
            }
        }
    }*/
}
