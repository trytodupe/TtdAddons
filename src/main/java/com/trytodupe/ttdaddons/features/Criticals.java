package com.trytodupe.ttdaddons.features;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class Criticals {

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
