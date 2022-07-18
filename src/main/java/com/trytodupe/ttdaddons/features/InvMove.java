package com.trytodupe.ttdaddons.features;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class InvMove {
    private boolean isWalking;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!ConfigHandler.invMove) return;
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            this.isWalking = true;
            mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(6));
            KeyBinding[] array = new KeyBinding[] { mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindSprint, mc.gameSettings.keyBindJump };
            for (int length = array.length, i = 0; i < length; ++i) {
                final KeyBinding keyBinding = array[i];
                KeyBinding.setKeyBindState(keyBinding.getKeyCode(), Keyboard.isKeyDown(keyBinding.getKeyCode()));
            }
        } else if (this.isWalking) {
            mc.thePlayer.sendQueue.addToSendQueue((Packet) new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
            this.isWalking = false;
        }
    }

    public static void toggle() {
        ConfigHandler.invMove = !ConfigHandler.invMove;
        if (ConfigHandler.invMove) ChatLib.chat("Inventory Move &aenabled");
        else ChatLib.chat("Inventory Move &cdisabled");
    }
}
