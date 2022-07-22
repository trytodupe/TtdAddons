package com.trytodupe.ttdaddons.features;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import com.trytodupe.ttdaddons.utils.ChatLib;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class RejoinGame {

    private static boolean rejoin;

    public static void setTime(String input) {
        try {
            int time = Integer.parseInt(input);
            if (time >= 0) ConfigHandler.rejoinTime = time;
            else throw new Exception();
            ChatLib.chat("Rejoin Time set to &b" + ConfigHandler.rejoinTime);
        }
        catch (Exception e) {
            ChatLib.chat("Please enter a valid number greater than 0.");
        }
    }

    public static void rejoinGame() {
        ChatLib.chat("Rejoining game...");
        rejoin = true;
        mc.thePlayer.sendChatMessage("/l mw");
    }

    @SubscribeEvent
    public void onJoinWorld(WorldEvent.Load event) {
        if(rejoin)
            new Thread(() -> {
                try {
                    rejoin = false;
                    Thread.sleep(ConfigHandler.rejoinTime);
                    mc.thePlayer.sendChatMessage("/rejoin");
                } catch (Exception ignored) {}
            }).start();
    }
}
