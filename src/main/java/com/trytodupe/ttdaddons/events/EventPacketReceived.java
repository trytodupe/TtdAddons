package com.trytodupe.ttdaddons.events;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class EventPacketReceived extends Event {
    public final Packet<?> packet;

    public EventPacketReceived(Packet<?> packet) {
        this.packet = packet;
    }
}

