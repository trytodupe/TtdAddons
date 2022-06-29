package com.trytodupe.ttdaddons.Features;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class NoSlow {

    public static void toggle() {
        ConfigHandler.noSlow = !ConfigHandler.noSlow;
        if (ConfigHandler.noSlow) ChatLib.chat("No Slow &aenabled");
        else ChatLib.chat("No Slow &cdisabled");
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_AIR || mc.thePlayer.getHeldItem() == null || !ConfigHandler.noSlow) return;
        ItemStack itemStack = mc.thePlayer.getHeldItem();
        if (itemStack.getItem().getRegistryName().toLowerCase().contains("sword") || itemStack.getItem().getRegistryName().toLowerCase().contains("milk") || itemStack.getItem() instanceof ItemFood || itemStack.getItem() instanceof ItemPotion) {
            event.setCanceled(true);
            if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
                mc.getNetHandler().getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(
                        new BlockPos(-1, -1, -1),
                        255,
                        itemStack,
                        0, 0, 0)
                );
            }
        }
    }
}
