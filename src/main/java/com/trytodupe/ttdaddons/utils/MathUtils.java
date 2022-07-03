package com.trytodupe.ttdaddons.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;

import javax.vecmath.Vector3d;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class MathUtils {
    public static float partialTicks = 0;
    public static float getX(Entity entity) {
        if (entity == null) return -10000;
        return (float) (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks);
    }

    public static float getY(Entity entity) {
        if (entity == null) return -10000;
        return (float) (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks);
    }

    public static float getZ(Entity entity) {
        if (entity == null) return -10000;
        return (float) (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks);
    }

    public static double distanceSquareFromPlayer(Vector3d v) {
        return distanceSquareFromPlayer(v.x, v.y, v.z);
    }

    public static double distanceSquareFromPlayer(double x, double y, double z) {
        double tx = getX(mc.thePlayer), ty = getY(mc.thePlayer) + getEyeHeight(mc.thePlayer), tz = getZ(mc.thePlayer);
        return distanceSquaredFromPoints(x, y, z, tx, ty, tz);
    }

    public static double distanceSquareFromPlayer(Entity entity) {
        double tx = getX(entity), ty = getY(entity), tz = getZ(entity);
        return distanceSquareFromPlayer(tx, ty, tz);
    }

    public static double distanceSquareFromPlayer(BlockPos pos) {
        double tx = pos.getX(), ty = pos.getY(), tz = pos.getZ();
        return distanceSquareFromPlayer(tx, ty, tz);
    }

    public static double distanceSquaredFromPoints(double x, double y, double z, double tx, double ty, double tz) {
        return (tx - x) * (tx - x) + (ty - y) * (ty - y) + (tz - z) * (tz - z);
    }

    public static float getEyeHeight(Entity entity) {
        if (entity == null) return 0;
        return entity.getEyeHeight();
    }

    public static float getYaw() {
        if (mc.thePlayer == null) return 0;
        float yaw = mc.thePlayer.rotationYaw;
        while (yaw < -180) yaw += 360;
        while (yaw >= 180) yaw -= 360;
        return yaw;
    }
}
