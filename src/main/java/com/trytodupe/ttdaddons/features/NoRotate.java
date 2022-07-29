package com.trytodupe.ttdaddons.features;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import com.trytodupe.ttdaddons.events.EventPacketReceived;
import com.trytodupe.ttdaddons.utils.ChatLib;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class NoRotate {

    private boolean doneLoadingTerrain;

    public static void toggle() {
        ConfigHandler.noRotate = !ConfigHandler.noRotate;
        if (ConfigHandler.noRotate) ChatLib.chat("No Rotate &aenabled");
        else ChatLib.chat("No Rotate &cdisabled");
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPosLookPacket(EventPacketReceived event) {
        if (!(event.packet instanceof S08PacketPlayerPosLook)) return;
        S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.packet;
        this.doneLoadingTerrain = true;
        if (packet.getPitch() == 0.0D) return;
        if (!ConfigHandler.noRotate || mc.thePlayer == null) return;
        event.setCanceled(true);
        double x = packet.getX(), y = packet.getY(), z = packet.getZ();
        float yaw = packet.getYaw(), pitch = packet.getPitch();
        if (packet.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X)) x += mc.thePlayer.posX;
        else mc.thePlayer.motionX = 0.0D;
        if (packet.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y)) y += mc.thePlayer.posY;
        else mc.thePlayer.motionY = 0.0D;
        if (packet.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Z)) z += mc.thePlayer.posZ;
        else mc.thePlayer.motionZ = 0.0D;
        if (packet.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) pitch += mc.thePlayer.rotationPitch;
        if (packet.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) yaw += mc.thePlayer.rotationYaw;
        mc.thePlayer.setPositionAndRotation(x, y, z, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
        C03PacketPlayer.C06PacketPlayerPosLook outPacket = new C03PacketPlayer.C06PacketPlayerPosLook(
                mc.thePlayer.posX, (mc.thePlayer.getEntityBoundingBox()).minY, mc.thePlayer.posZ,
                yaw, pitch, false);
        mc.getNetHandler().getNetworkManager().sendPacket(outPacket);
        if (!this.doneLoadingTerrain) {
            mc.thePlayer.prevPosX = mc.thePlayer.posX;
            mc.thePlayer.prevPosY = mc.thePlayer.posY;
            mc.thePlayer.prevPosZ = mc.thePlayer.posZ;
            mc.thePlayer.closeScreen();
        }
    }

    @SubscribeEvent
    public void onRespawnPacket(EventPacketReceived event) {
        if (event.packet instanceof net.minecraft.network.play.server.S07PacketRespawn) {
            this.doneLoadingTerrain = false;
        }
    }
}
