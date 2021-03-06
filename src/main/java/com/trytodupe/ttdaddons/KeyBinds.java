package com.trytodupe.ttdaddons;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import com.trytodupe.ttdaddons.features.TunnelMiner;
import com.trytodupe.ttdaddons.features.LegitSpeed;
import com.trytodupe.ttdaddons.features.NoSlow;
import com.trytodupe.ttdaddons.features.RejoinGame;
import com.trytodupe.ttdaddons.features.Nuker;
import com.trytodupe.ttdaddons.features.SpeedMine;
import com.trytodupe.ttdaddons.objects.KeyBind;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class KeyBinds {

    private final KeyBind legitSpeed = new KeyBind("Legit Speed", Keyboard.KEY_NONE);
    private final KeyBind speedMine = new KeyBind("Speed Mine", Keyboard.KEY_NONE);
    private final KeyBind noSlow = new KeyBind("No Slow", Keyboard.KEY_NONE);
    private final KeyBind snowNuker = new KeyBind("Nuker", Keyboard.KEY_NONE);
    private final KeyBind rejoinGame = new KeyBind("Rejoin Game", Keyboard.KEY_NONE);
    private final KeyBind tunnelMiner = new KeyBind("Tunnel Miner", Keyboard.KEY_NONE);
    private final KeyBind suicide = new KeyBind("/kill", Keyboard.KEY_NONE);
    

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (legitSpeed.isPressed()) LegitSpeed.toggle();
        if (speedMine.isPressed()) SpeedMine.toggle();
        if (noSlow.isPressed()) NoSlow.toggle();
        if (snowNuker.isPressed()) Nuker.toggle();
        if (rejoinGame.isPressed()) RejoinGame.rejoinGame();
        if (tunnelMiner.isPressed()) TunnelMiner.toggle();
        if (suicide.isPressed()) {
            if (mc.thePlayer == null) return;
            mc.thePlayer.sendChatMessage("/kill");
            }
        ConfigHandler.saveConfig();
    }
}
