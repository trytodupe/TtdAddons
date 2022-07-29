/*
package com.trytodupe.ttdaddons.features;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;
import com.trytodupe.ttdaddons.utils.RenderUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class AutoScatha {


    private static BlockPos blockToMine;
    private static EnumFacing dir;

    public static void toggle() {
        ConfigHandler.autoScatha = !ConfigHandler.autoScatha;
        if (ConfigHandler.nuker) {
            EnumFacing dir = mc.thePlayer.getHorizontalFacing();
            ChatLib.chat("Auto Scatha &aenabled");
        }
        else {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
            ChatLib.chat("Auto Scatha &cdisabled");
        }
    }

    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event) {
        if (!ConfigHandler.nuker) return;
        //if (mc.thePlayer.getHeldItem() == null || !mc.thePlayer.getHeldItem().getItem().getUnlocalizedName().contains("shovel")) return;
        blockToMine = shouldMine();
        if (blockToMine != null) {
            //RenderUtils.drawBlockBox(closestBlock, new Color(128, 128, 128), 3, event.partialTicks);
            RenderUtils.drawBlockBox(blockToMine, new Color(255, 64, 236), 3, event.partialTicks);
        }
    }

    private void RotateToBlock(BlockPos block) {
        double diffX = block.getX() - mc.thePlayer.posX + 0.5;
        double diffY = block.getY() - mc.thePlayer.posY + 0.5 - mc.thePlayer.getEyeHeight();
        double diffZ = block.getZ() - mc.thePlayer.posZ + 0.5;
        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float pitch = (float) -Math.atan2(dist, diffY);
        float yaw = (float) Math.atan2(diffZ, diffX);
        pitch = (float) wrapAngleTo180((pitch * 180F /(float) Math.PI + 90)*-1);
        yaw = (float) wrapAngleTo180((yaw * 180 /(float) Math.PI) - 90);

        mc.thePlayer.rotationPitch = pitch;
        mc.thePlayer.rotationYaw = yaw;
    }

    private static float wrapAngleTo180(float angle) {
        return (float) (angle - Math.floor(angle / 360 + 0.5) * 360);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.theWorld == null) return;
        if (mc.thePlayer == null) return;
        if (!ConfigHandler.autoScatha) return;

    }

    private BlockPos shouldMine() {
        if(mc.theWorld == null) return null;
        if(mc.thePlayer == null) return null;
        int r = 5;
        BlockPos playerPos = mc.thePlayer.getPosition();
        playerPos.add(1, 1, 1);
        BlockPos farPos;
        switch (dir) {
            case NORTH:
                farPos =
                break;
            case EAST:

                break;
            case SOUTH:
                Math.floor(mc.thePlayer.getPosition().getX());
                break;
            case WEST:

                break;
        }


        int x = (int) Math.floor(mc.thePlayer.posX);
        int z =  (int) Math.floor(mc.thePlayer.posZ);

    }
}
*/
