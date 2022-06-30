package com.trytodupe.ttdaddons.Features;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.Objects.Inventory;
import com.trytodupe.ttdaddons.utils.ChatLib;
import com.trytodupe.ttdaddons.utils.ScoreboardUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class AutoReadyUp {

    private static Inventory openedInventory = null;

    private static boolean readyUp = false;

    private static boolean dungeonStarted = false;

    public static void toggle() {
        ConfigHandler.autoReadyUp = !ConfigHandler.autoReadyUp;
        if (ConfigHandler.autoReadyUp) ChatLib.chat("Auto Ready Up &aenabled");
        else ChatLib.chat("Auto Ready Up &cdisabled");
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (ConfigHandler.autoReadyUp &&  !dungeonStarted) {
            String title;
            if (mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1) == null) title = " ";
            else title = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1).getDisplayName();
            if ((StringUtils.stripControlCodes(title).contains("SKYBLOCK") && ScoreboardUtils.scoreboardContains("The Catacombs") && !ScoreboardUtils.scoreboardContains("Queue")) || ScoreboardUtils.scoreboardContains("Dungeon Cleared:"))
                if (!readyUp)
                    for (Entity entity : mc.theWorld.getLoadedEntityList()) {
                        if (entity instanceof net.minecraft.entity.item.EntityArmorStand &&
                                entity.hasCustomName() && entity.getCustomNameTag().equals("§bMort")) {
                            List<Entity> possibleEntities = entity.getEntityWorld().getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().expand(0.0D, 3.0D, 0.0D), e -> e instanceof EntityPlayer);
                            if (!possibleEntities.isEmpty()) {
                                mc.playerController.interactWithEntitySendPacket((EntityPlayer)mc.thePlayer, possibleEntities.get(0));
                                readyUp = true;
                            }
                        }
                    }
            Inventory inventory = openedInventory;
            String chestName = inventory.getName();
            if (readyUp && chestName != null) {
                if (chestName.equals("Start Dungeon?")) {
                    mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, 13, 2, 0, (EntityPlayer)mc.thePlayer);
                    return;
                }
                if (chestName.startsWith("Catacombs - "))
                    for (Slot slot : mc.thePlayer.openContainer.inventorySlots) {
                        if (slot.getStack() != null && slot.getStack().getDisplayName().contains(mc.thePlayer.getName())) {
                            mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, slot.slotNumber, 2, 0, (EntityPlayer)mc.thePlayer);
                            mc.thePlayer.closeScreen();
                            break;
                        }
                    }
            }
        }
    }

    @SubscribeEvent
    public void onTickUpdateInventory(TickEvent.ClientTickEvent event) {
        EntityPlayerSP player = mc.thePlayer;
        if (player == null || player.openContainer == null) openedInventory = null;
        else openedInventory = new Inventory(player.openContainer);
    }

    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
        if (!dungeonStarted && event.message.getUnformattedText().contains("Dungeon starts in 4 seconds."))
            dungeonStarted = true;
    }

    @SubscribeEvent
    public void onJoinWorld(WorldEvent.Load event) {
        readyUp = false;
        dungeonStarted = false;
    }
}