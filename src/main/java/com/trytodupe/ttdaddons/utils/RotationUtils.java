package com.trytodupe.ttdaddons.utils;

import com.trytodupe.ttdaddons.Mixins.PlayerSPAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class RotationUtils {
    public static float lastReportedPitch;

    public static float[] getAngles(Vec3 vec) {
        double diffX = vec.xCoord - mc.thePlayer.posX;
        double diffY = vec.yCoord - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
        double diffZ = vec.zCoord - mc.thePlayer.posZ;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[] { mc.thePlayer.rotationYaw +
                MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch) };
    }

    public static float[] getServerAngles(Vec3 vec) {
        double diffX = vec.xCoord - mc.thePlayer.posX;
        double diffY = vec.yCoord - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
        double diffZ = vec.zCoord - mc.thePlayer.posZ;
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[] { ((PlayerSPAccessor)mc.thePlayer)
                .getLastReportedYaw() + MathHelper.wrapAngleTo180_float(yaw - ((PlayerSPAccessor)mc.thePlayer).getLastReportedYaw()), ((PlayerSPAccessor)mc.thePlayer)
                .getLastReportedPitch() + MathHelper.wrapAngleTo180_float(pitch - ((PlayerSPAccessor)mc.thePlayer).getLastReportedPitch()) };
    }

    public static float[] getBowAngles(Entity entity) {
        double xDelta = (entity.posX - entity.lastTickPosX) * 0.4D;
        double zDelta = (entity.posZ - entity.lastTickPosZ) * 0.4D;
        double d = mc.thePlayer.getDistanceToEntity(entity);
        d -= d % 0.8D;
        double xMulti = d / 0.8D * xDelta;
        double zMulti = d / 0.8D * zDelta;
        double x = entity.posX + xMulti - mc.thePlayer.posX;
        double z = entity.posZ + zMulti - mc.thePlayer.posZ;
        double y = mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - entity.posY + entity.getEyeHeight();
        double dist = mc.thePlayer.getDistanceToEntity(entity);
        float yaw = (float)Math.toDegrees(Math.atan2(z, x)) - 90.0F;
        double d1 = MathHelper.sqrt_double(x * x + z * z);
        float pitch = (float)-(Math.atan2(y, d1) * 180.0D / Math.PI) + (float)dist * 0.11F;
        return new float[] { yaw, -pitch };
    }

    public static boolean isWithinFOV(EntityLivingBase entity, double fov) {
        float yawDifference = Math.abs(getAngles((Entity)entity)[0] - mc.thePlayer.rotationYaw);
        return (yawDifference < fov && yawDifference > -fov);
    }

    public static float getYawDifference(EntityLivingBase entity1, EntityLivingBase entity2) {
        return Math.abs(getAngles((Entity)entity1)[0] - getAngles((Entity)entity2)[0]);
    }

    public static float getYawDifference(EntityLivingBase entity1) {
        return Math.abs(mc.thePlayer.rotationYaw - getAngles((Entity)entity1)[0]);
    }

    public static boolean isWithinPitch(EntityLivingBase entity, double pitch) {
        float pitchDifference = Math.abs(getAngles((Entity)entity)[1] - mc.thePlayer.rotationPitch);
        return (pitchDifference < pitch && pitchDifference > -pitch);
    }

    public static float[] getAngles(Entity en) {
        return getAngles(new Vec3(en.posX, en.posY + en.getEyeHeight() - en.height / 1.5D + 0.5D, en.posZ));
    }

    public static float[] getServerAngles(Entity en) {
        return getServerAngles(new Vec3(en.posX, en.posY + en.getEyeHeight() - en.height / 1.5D + 0.5D, en.posZ));
    }
}
