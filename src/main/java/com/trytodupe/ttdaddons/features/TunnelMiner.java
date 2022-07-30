package com.trytodupe.ttdaddons.features;

import com.trytodupe.ttdaddons.events.EventMotionUpdate;
import com.trytodupe.ttdaddons.utils.ChatLib;
import com.trytodupe.ttdaddons.utils.RenderUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

//todo: bedrock, autotools
public class TunnelMiner {

    private static boolean enable = false;
    private static int currentDamage, tool = -1;
    private static BlockPos blockToMine;
    private static BlockPos lastBlock;
    private static EnumFacing dir;

    public static void toggle() {
        enable = !enable;
        if (enable) {
            dir = mc.thePlayer.getHorizontalFacing();
            ChatLib.chat("Tunnel Miner &aenabled");
        }
        else {
            currentDamage = 0;
            blockToMine = null;
            lastBlock = null;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
            ChatLib.chat("Tunnel Miner &cdisabled");
        }
    }

    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event) {
        if (!enable) return;
        if (blockToMine != null) {
            RenderUtils.drawBlockBox(blockToMine, new Color(255, 64, 236), 3, event.partialTicks);
        }
    }

    @SubscribeEvent
    public void onMovePre(EventMotionUpdate.Pre event) {
        if(mc.theWorld == null) return;
        if(mc.thePlayer == null) return;
        if (!enable) return;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true); //todo: to remove
        //if (blockToMine == null || mc.theWorld.getBlockState(blockToMine).getBlock().equals(Blocks.air))
        blockToMine = shouldMine();
        if (blockToMine != null) {
            IBlockState blockState = mc.theWorld.getBlockState(blockToMine);
            if (blockState.getBlock().equals(Blocks.bedrock)) {
                ChatLib.chat("Reached bedrock.");
                toggle();
                return;
            }
            tool = getBestItem(blockToMine);
            double diffX = blockToMine.getX() - mc.thePlayer.posX + 0.5;
            double diffY = blockToMine.getY() - mc.thePlayer.posY + 0.5 - mc.thePlayer.getEyeHeight();
            double diffZ = blockToMine.getZ() - mc.thePlayer.posZ + 0.5;
            double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);

            float pitch = (float) -Math.atan2(dist, diffY);
            float yaw = (float) Math.atan2(diffZ, diffX);
            pitch = (float) wrapAngleTo180((pitch * 180F / (float) Math.PI + 90) * -1);
            /*yaw = (float) wrapAngleTo180((yaw * 180 / (float) Math.PI) - 90);
            event.pitch = pitch;
            event.yaw = yaw;*/
            mc.thePlayer.rotationPitch = pitch;
        } else {
            mc.thePlayer.rotationPitch = wrapAngleTo180(0);
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);   //todo: to remove
        }
    }

/*    @SubscribeEvent
    public void onMovePost(EventMotionUpdate.Post event) {
        if (blockToMine != null && mc.thePlayer != null) {
            if (lastBlock == null || !lastBlock.equals(blockToMine))
                currentDamage = 0;
            lastBlock = blockToMine;
            MovingObjectPosition fake = mc.objectMouseOver;
            fake.hitVec = new Vec3(blockToMine);
            EnumFacing enumFacing = fake.sideHit;
            if (currentDamage == 0) {
                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockToMine, enumFacing));
                ChatLib.chat("starting digging");
            }
            swingItem();
            currentDamage++;
        }
    }*/

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (enable) {
            if (blockToMine != null && mc.thePlayer != null) {
                if (lastBlock == null || !lastBlock.equals(blockToMine))
                    currentDamage = 0;
                lastBlock = blockToMine;
                MovingObjectPosition fake = mc.objectMouseOver;
                fake.hitVec = new Vec3(blockToMine);
                EnumFacing enumFacing = fake.sideHit;
                if (tool != -1 && mc.thePlayer.inventory.currentItem != tool) {
                    mc.thePlayer.inventory.currentItem = tool;
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(tool));
                }

            /*if (currentDamage == 0) {
                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockToMine, enumFacing *//*EnumFacing.DOWN*//*));
                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockToMine, enumFacing *//*EnumFacing.DOWN*//*));
                ChatLib.chat("starting digging");
            }
            mc.playerController.onPlayerDamageBlock(blockToMine, enumFacing);
            swingItem();
            currentDamage++;
            */
            }
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true); //todo: to remove
        }
    }

    private static float wrapAngleTo180(float angle) {
        return (float) (angle - Math.floor(angle / 360 + 0.5) * 360);
    }

    public static void swingItem() {
        if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit == null) mc.thePlayer.swingItem();
    }

    private BlockPos shouldMine() {
        if(mc.theWorld == null) return null;
        if(mc.thePlayer == null) return null;
        BlockPos closePos;
        BlockPos farPos;
        switch (dir) {
            case NORTH:
                closePos = new BlockPos(Math.floor(mc.thePlayer.posX), Math.floor(mc.thePlayer.posY), Math.floor(mc.thePlayer.posZ - 1d));
                farPos = new BlockPos(Math.floor(mc.thePlayer.posX), Math.floor(mc.thePlayer.posY + 1), Math.floor(mc.thePlayer.posZ - 4.5d));
                mc.thePlayer.rotationYaw = (float) wrapAngleTo180(180); //todo: to remove
                break;
            case EAST:
                closePos = new BlockPos(Math.floor(mc.thePlayer.posX + 1d), Math.floor(mc.thePlayer.posY), Math.floor(mc.thePlayer.posZ));
                farPos = new BlockPos(Math.floor(mc.thePlayer.posX + 4.5d), Math.floor(mc.thePlayer.posY + 1), Math.floor(mc.thePlayer.posZ));
                mc.thePlayer.rotationYaw = (float) wrapAngleTo180(-90); //todo: to remove
                break;
            case SOUTH:
                closePos = new BlockPos(Math.floor(mc.thePlayer.posX), Math.floor(mc.thePlayer.posY), Math.floor(mc.thePlayer.posZ + 1d));
                farPos = new BlockPos(Math.floor(mc.thePlayer.posX), Math.floor(mc.thePlayer.posY + 1), Math.floor(mc.thePlayer.posZ + 4.5d));
                mc.thePlayer.rotationYaw = (float) wrapAngleTo180(0); //todo: to remove
                break;
            case WEST:
                closePos = new BlockPos(Math.floor(mc.thePlayer.posX - 1d), Math.floor(mc.thePlayer.posY), Math.floor(mc.thePlayer.posZ));
                farPos = new BlockPos(Math.floor(mc.thePlayer.posX - 4.5d), Math.floor(mc.thePlayer.posY + 1), Math.floor(mc.thePlayer.posZ));
                mc.thePlayer.rotationYaw = (float) wrapAngleTo180(90); //todo: to remove
                break;
            default:
                closePos = new BlockPos(Math.floor(mc.thePlayer.posX), Math.floor(mc.thePlayer.posY), Math.floor(mc.thePlayer.posZ));
                farPos = closePos;
        }
        double smallest = 9999;
        BlockPos closest = null;
        for (BlockPos blockPos : BlockPos.getAllInBox(closePos, farPos)) {
            IBlockState blockState = mc.theWorld.getBlockState(blockPos);
            if (blockState.getBlock().equals(Blocks.air) || blockState.getBlock().equals(Blocks.water) || blockState.getBlock().equals(Blocks.flowing_water) || blockState.getBlock().equals(Blocks.lava) || blockState.getBlock().equals(Blocks.flowing_lava)) continue;

            double dist = new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5).distanceTo(new Vec3(mc.thePlayer.getPositionVector().xCoord, mc.thePlayer.getPositionVector().yCoord + 1d, mc.thePlayer.getPositionVector().zCoord));
            if (dist < smallest) {
                smallest = dist;
                closest = blockPos;
            }
        }
        return closest;
    }

    private int getBestItem(final BlockPos blockPos) {
        float bestSpeed = 1F;
        int bestSlot = -1;

        final IBlockState blockState = mc.theWorld.getBlockState(blockPos);

        for (int i = 0; i < 9; i++) {
            final ItemStack item = mc.thePlayer.inventory.getStackInSlot(i);
            if (item == null) continue;

            final float speed = item.getStrVsBlock(blockState.getBlock());

            if (speed > bestSpeed) {
                bestSpeed = speed;
                bestSlot = i;
            }
        }

        return bestSlot;
    }
}

