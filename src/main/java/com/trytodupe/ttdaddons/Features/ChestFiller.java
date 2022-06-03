package com.trytodupe.ttdaddons.Features;

import com.trytodupe.ttdaddons.utils.ChatLib;
import com.trytodupe.ttdaddons.Objects.Inventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.util.StringUtils;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;


public class ChestFiller {
    public static final Minecraft mc = Minecraft.getMinecraft();
    private static Inventory openedInventory = null;
    private static boolean enabled = false, done = false;
    private Thread pushingThread = null;
    private static int lastId = -1;

    private static String name = null;

    public static void disable() {
        if (enabled) ChatLib.chat("Chest Filler &cdisabled");
        lastId = -1;
        enabled = false;
        done = false;
        name = null;
    }

    public static void enable(String input) {
        name = input;
        ChatLib.chat("Chest Filler &aenabled");
        enabled = true;
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
                            if (inventory.getItemInSlot(i) == null) emptySlots.add(inventory.getSlots().get(i));
                        }
                        //inv slots: 54-89
                        for (i = 54; i < 90; i++) {
                            if (inventory.getItemInSlot(i) == null) continue;
                            amount = inventory.getItemInSlot(i).stackSize;
                            //if (inventory.getItemInSlot(i).getItem().equals(Item.getByNameOrId("minecraft:prismarine_crystals"))
                            if (StringUtils.stripControlCodes(inventory.getItemInSlot(i).getDisplayName()).contains(name)
                                    && amount >= emptySlots.size()) {
                                isEmpty = false;
                                break;
                            }
                        }
                        if (emptySlots.isEmpty()) return;
                        if (!isEmpty) {
                            click(inventory, i, 0, 0, 0);
                            Thread.sleep(200);
                            click(inventory, -999, 5, 4, 0);
                            for (Slot slot1 : emptySlots) click(inventory, slot1.slotNumber, 5, 5, 0);
                            click(inventory, -999, 5, 6, 0);
                            Thread.sleep(100);
                            //click(inventory, i, 0, 0, 0);
                            //Thread.sleep(100);
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
