package com.trytodupe.ttdaddons.Features;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;
import com.trytodupe.ttdaddons.Objects.Inventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.inventory.Slot;
import net.minecraft.util.StringUtils;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;

import static com.trytodupe.ttdaddons.TtdAddons.mc;


public class ChestFiller {
    private static Inventory openedInventory = null;
    private static boolean enabled = false, done = false;
    private Thread pushingThread = null;
    private static int lastId = -1;

    public static void disable() {
        if (enabled) ChatLib.chat("Chest Filler &cdisabled");
        lastId = -1;
        enabled = false;
        done = false;
    }

    public static void setItem(String input) {
        ConfigHandler.chestFillerItemName = input;
        ChatLib.chat("Chest Filler item name set to: &b" + ConfigHandler.chestFillerItemName + "&r");
    }

    public static void toggleSix() {
        ConfigHandler.chestFiller6 = !ConfigHandler.chestFiller6;
        if (ConfigHandler.chestFiller6) ChatLib.chat("Chest Filler six mode &aenabled");
        else ChatLib.chat("Chest Filler six mode &cdisabled");
    }

    public static void enable() {
        if (ConfigHandler.chestFillerItemName.equals("")) {
            ChatLib.chat("Use /trytodupe fill [itemName] to set item first!");
            return;
        }
        ChatLib.chat("Chest Filler &aenabled&r (&b" + ConfigHandler.chestFillerItemName + "&r)");
        enabled = true;
    }

    public static boolean isToggled() {
        return enabled;
    }

    public void click(Inventory inventory, int slot, int mode, int button, int incrementWindowId) {
        int windowId = inventory.getWindowId() + incrementWindowId;
        mc.playerController.windowClick(
                windowId, slot, button, mode, mc.thePlayer
        );
    }

    @SubscribeEvent
    public void onTickUpdateInventory(TickEvent.ClientTickEvent event) {
        EntityPlayerSP player = mc.thePlayer;
        if (player == null || player.openContainer == null) openedInventory = null;
        else openedInventory = new Inventory(player.openContainer);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        disable();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        Inventory inventory = openedInventory;
        if (inventory == null) return;
        if (lastId == -1) lastId = inventory.getWindowId();
        else if (lastId != inventory.getWindowId()) {
            lastId = inventory.getWindowId();
            done = false;
        }
        if (enabled && !done && inventory.getName().equals("Large Chest")) {
            done = true;
            if (pushingThread == null || !pushingThread.isAlive()) {
                pushingThread = new Thread(() -> {
                    try {
                        Thread.sleep(200);
                        if (openedInventory == null || !inventory.getName().equals("Large Chest"))
                            return;
                        int i, amount;
                        boolean isEmpty = true;
                        HashSet<Slot> emptySlots = new HashSet<>();
                        for (i = 0; i < 54; i++) {
                            if (ConfigHandler.chestFiller6 && i == 5) continue;
                            if (inventory.getItemInSlot(i) == null) emptySlots.add(inventory.getSlots().get(i));
                        }
                        //inv slots: 54-89
                        for (i = 54; i < 90; i++) {
                            if (inventory.getItemInSlot(i) == null) continue;
                            amount = inventory.getItemInSlot(i).stackSize;
                            if (StringUtils.stripControlCodes(inventory.getItemInSlot(i).getDisplayName()).toLowerCase().contains(ConfigHandler.chestFillerItemName.toLowerCase())
                                    && amount >= emptySlots.size()) {
                                isEmpty = false;
                                break;
                            }
                        }
                        if (emptySlots.isEmpty() && inventory.getItemInSlot(5) != null) return;
                        if (!isEmpty) {
                            click(inventory, i, 0, 0, 0);
                            Thread.sleep(200);
                            click(inventory, -999, 5, 4, 0);
                            for (Slot slot1 : emptySlots) click(inventory, slot1.slotNumber, 5, 5, 0);
                            click(inventory, -999, 5, 6, 0);
                            Thread.sleep(50);
                            if (inventory.getItemInSlot(5) == null) click(inventory, 5, 0, 1, 0);
                            Thread.sleep(50);
                            mc.thePlayer.closeScreen();
                        } else {
                            ChatLib.chat("Not enough items.");
                            disable();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                pushingThread.start();
            }
        }
    }

    @SubscribeEvent
    public void onTickMove(TickEvent.ClientTickEvent event) {
        if (openedInventory == null) return;
        if ((!openedInventory.getName().equals("Large Chest")) && (pushingThread != null)) pushingThread.interrupt();
    }
}
