package com.trytodupe.ttdaddons.Features;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.Objects.KeyBind;
import com.trytodupe.ttdaddons.utils.ChatLib;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class LegitSpeed {

    public static void toggle() {
        ConfigHandler.legitSpeed = !ConfigHandler.legitSpeed;
        if (ConfigHandler.legitSpeed) ChatLib.chat("Legit Speed &aenabled");
        else ChatLib.chat("Legit Speed &cdisabled");
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onJump(LivingEvent.LivingJumpEvent event) {
        if (ConfigHandler.legitSpeed) {
            if (mc.thePlayer == null || getSpeedEffect() <= 0 || mc.thePlayer.moveForward <= 0.0F || !mc.thePlayer.isSprinting() || getSpeedDuration() <= 20)
                return;
            if (event.entity instanceof net.minecraft.entity.player.EntityPlayer && event.entity == mc.thePlayer) {
                mc.thePlayer.motionX *= (1.0F + getSpeedEffect() * 0.15F);
                mc.thePlayer.motionZ *= (1.0F + getSpeedEffect() * 0.15F);
            }
        }
    }

    public static int getSpeedEffect() {
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed))
            return mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        return 0;
    }

    public static int getSpeedDuration() {
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed))
            return mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getDuration();
        return 0;
    }
}
