package com.trytodupe.ttdaddons;

import com.trytodupe.ttdaddons.Features.LegitSpeed;
import com.trytodupe.ttdaddons.Features.NoSlow;
import com.trytodupe.ttdaddons.Features.SpeedMine;
import com.trytodupe.ttdaddons.Objects.KeyBind;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class KeyBinds {

    private final KeyBind legitSpeed = new KeyBind("Legit Speed", Keyboard.KEY_NONE);
    private final KeyBind speedMine = new KeyBind("Speed Mine", Keyboard.KEY_NONE);
    private final KeyBind noSlow = new KeyBind("No Slow", Keyboard.KEY_NONE);

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (legitSpeed.isPressed()) LegitSpeed.toggle();
        if (speedMine.isPressed()) SpeedMine.toggle();
        if (noSlow.isPressed()) NoSlow.toggle();
    }
}