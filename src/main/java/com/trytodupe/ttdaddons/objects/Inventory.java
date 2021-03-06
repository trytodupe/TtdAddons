package com.trytodupe.ttdaddons.objects;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;

public class Inventory {

    private final Container container;

    public Inventory(Container container) {
        this.container = container;
    }

    public int getWindowId() {
        return container.windowId;
    }

    public int getSize() {
        return container.inventoryItemStacks.size();
    }

    public List<Slot> getSlots() {
        return container.inventorySlots;
    }

    public final ItemStack getItemInSlot(int slot) {
        if (getSize() <= slot) return null;
        return container.getSlot(slot).getStack();
    }

    public final String getName() {
        if (container instanceof ContainerChest) {
            return ((ContainerChest) container).getLowerChestInventory().getName();
        }
        return "container";
    }
}
