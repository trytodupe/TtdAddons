package com.trytodupe.ttdaddons.features;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import com.trytodupe.ttdaddons.events.EventMotionUpdate;
import com.trytodupe.ttdaddons.utils.ChatLib;
import com.trytodupe.ttdaddons.utils.RenderUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.Color;
import java.util.ArrayList;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class Nuker {

    private static boolean enable = false;
    private static ArrayList<BlockPos> broken = new ArrayList<>();
    private static BlockPos closestBlock;
    private static int ticks = 0;

    public static void toggle() {
        enable = !enable;
        if (enable) ChatLib.chat("Nuker &aenabled");
        else {
            broken.clear();
            closestBlock = null;
            ChatLib.chat("Nuker &cdisabled");
        }
    }

    public static void toggleSnow() {
        ConfigHandler.nukeSnow = !ConfigHandler.nukeSnow;
        if (ConfigHandler.nukeSnow) ChatLib.chat("Nuker &awill now nuke&r snow");
        else ChatLib.chat("Nuker &cwill now not nuke&r snow");
    }

    public static void toggleDirt() {
        ConfigHandler.nukeDirt = !ConfigHandler.nukeDirt;
        if (ConfigHandler.nukeDirt) ChatLib.chat("Nuker &awill now nuke&r dirt");
        else ChatLib.chat("Nuker &cwill now not nuke&r dirt");
    }

    public static void setHeight(String input) {
        try {
            int height = Integer.parseInt(input);
            if (height >= 0 && height <= 5) ConfigHandler.nukerHeight = height;
            else throw new Exception();
            ChatLib.chat("Nuker height set to &b" + ConfigHandler.nukerHeight);
        }
        catch (Exception e) {
            ChatLib.chat("Please enter a valid number between 0 to 5.");
        }
    }

    public static void setDepth(String input) {
        try {
            int depth = Integer.parseInt(input);
            if (depth >= 0 && depth <= 3) ConfigHandler.nukerDepth = depth;
            else throw new Exception();
            ChatLib.chat("Nuker depth set to &b" + ConfigHandler.nukerDepth);
        }
        catch (Exception e) {
            ChatLib.chat("Please enter a valid number between 0 to 3.");
        }
    }


    @SubscribeEvent
    public void onMovePre(EventMotionUpdate.Pre event) {
        if(mc.theWorld == null) return;
        if(mc.thePlayer == null) return;
        if (!enable) return;
        if (mc.thePlayer.getHeldItem() == null || !mc.thePlayer.getHeldItem().getItem().getUnlocalizedName().contains("shovel")) return;
        if (broken.size() > 10) broken.clear();
        if (mc.thePlayer.ticksExisted % 20 == 0) broken.clear();
        closestBlock = closestBlock();
        if (closestBlock != null) {
            double diffX = closestBlock.getX() - mc.thePlayer.posX + 0.5;
            double diffY = closestBlock.getY() - mc.thePlayer.posY + 0.5 - mc.thePlayer.getEyeHeight();
            double diffZ = closestBlock.getZ() - mc.thePlayer.posZ + 0.5;
            double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);

            float pitch = (float) -Math.atan2(dist, diffY);
            float yaw = (float) Math.atan2(diffZ, diffX);
            pitch = (float) wrapAngleTo180((pitch * 180F / (float) Math.PI + 90) * -1);
            yaw = (float) wrapAngleTo180((yaw * 180 / (float) Math.PI) - 90);
            event.pitch = pitch;
            event.yaw = yaw;
        }
    }

    @SubscribeEvent
    public void onMovePost(EventMotionUpdate.Post event) {
        if (closestBlock != null) {
            MovingObjectPosition fake = mc.objectMouseOver;
            fake.hitVec = new Vec3(closestBlock);
            EnumFacing enumFacing = fake.sideHit;
            if (enumFacing != null && mc.thePlayer != null) {
                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, closestBlock, enumFacing));
                swingItem();
                broken.add(closestBlock);
            }
        }
    }

    private static float wrapAngleTo180(float angle) {
        return (float) (angle - Math.floor(angle / 360 + 0.5) * 360);
    }

    public static void swingItem() {
        if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit == null) mc.thePlayer.swingItem();
    }

    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event) {
        if (!enable) return;
        if (mc.thePlayer.getHeldItem() == null || !mc.thePlayer.getHeldItem().getItem().getUnlocalizedName().contains("shovel")) return;
        closestBlock = closestBlock();
        if (closestBlock != null && ticks % 2 == 0) {
            RenderUtils.drawBlockBox(closestBlock, new Color(128, 128, 128), 3, event.partialTicks);
        }
    }

    private BlockPos closestBlock() {
        if(mc.theWorld == null) return null;
        if(mc.thePlayer == null) return null;

        int r = 5;
        BlockPos playerPos = mc.thePlayer.getPosition();
        playerPos.add(0, 1, 0);
        Vec3 playerVec = mc.thePlayer.getPositionVector();
        Vec3i vec3i = new Vec3i(r, 1 + ConfigHandler.nukerHeight, r);
        Vec3i vec3i2 = new Vec3i(r, ConfigHandler.nukerDepth, r);
        ArrayList<Vec3> blocks = new ArrayList<Vec3>();

        for (BlockPos blockPos : BlockPos.getAllInBox(playerPos.add(vec3i), playerPos.subtract(vec3i2))) {
            IBlockState blockState = mc.theWorld.getBlockState(blockPos);
            if (ConfigHandler.nukeSnow && blockState.getBlock() == Blocks.snow && !broken.contains(blockPos))
                blocks.add(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5));
            if (ConfigHandler.nukeDirt && (blockState.getBlock() == Blocks.dirt || blockState.getBlock() == Blocks.grass) && !broken.contains(blockPos))
                blocks.add(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5));
        }

        double smallest = 9999;
        Vec3 closest = null;
        for (Vec3 block : blocks) {
            double dist = block.distanceTo(playerVec);
            if (dist < smallest) {
                smallest = dist;
                closest = block;
            }
        }

        if (closest != null && smallest < 5)
            return new BlockPos(closest.xCoord, closest.yCoord, closest.zCoord);
        return null;
    }
}
