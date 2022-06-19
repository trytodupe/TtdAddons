package com.trytodupe.ttdaddons.Features;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class HeadRotation {

    public static void toggle() {
        ConfigHandler.headRotation = !ConfigHandler.headRotation;
        if (ConfigHandler.headRotation) ChatLib.chat("Camera Rotation &aenabled");
        else ChatLib.chat("Camera Rotation &cdisabled");
    }

    public static float clientYaw = 0.0F;

    public static float prevClientPitch = 0.0F;

    public static float clientPitch = 0.0F;

    public static float serverYaw = 0.0F;

    public static float serverPitch = 0.0F;

    public static float prevServerYaw = 0.0F;

    public static float prevServerPitch = 0.0F;

    public static boolean shouldRotate() {
        return clientYaw != serverYaw || clientPitch != serverPitch;
    }

    public static void onPacket(Packet<?> packet) {
        if (packet instanceof C03PacketPlayer) {
            C03PacketPlayer packetPlayer = (C03PacketPlayer)packet;
            if (packetPlayer.getRotating()) {
                clientYaw = mc.thePlayer.rotationYaw;
                if (packetPlayer.getYaw() != serverYaw) {
                    prevServerYaw = serverYaw;
                    serverYaw = packetPlayer.getYaw();
                }
                clientPitch = mc.thePlayer.rotationPitch;
                if (packetPlayer.getPitch() != serverPitch) {
                    prevServerPitch = serverPitch;
                    serverPitch = packetPlayer.getPitch();
                }
            }
        }
    }
}
