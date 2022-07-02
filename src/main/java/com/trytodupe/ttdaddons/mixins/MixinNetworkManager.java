package com.trytodupe.ttdaddons.mixins;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import com.trytodupe.ttdaddons.features.HeadRotation;
import com.trytodupe.ttdaddons.utils.ReflectionUtils;
import com.trytodupe.ttdaddons.mixins.AccessorC03PacketPlayer;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NetworkManager.class})
public abstract class MixinNetworkManager implements AccessorC03PacketPlayer {
    @Inject(method = {"channelRead0"}, at = {@At("HEAD")})
    private void channelRead0(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo) {
        HeadRotation.onPacket(packet);
    }

    @Inject(method = {"sendPacket(Lnet/minecraft/network/Packet;)V"}, at = {@At("HEAD")})
    private void sendPacket(Packet<?> packet, CallbackInfo callbackInfo) {
        /*
        if (packet instanceof C03PacketPlayer && ConfigHandler.criticals) {
            ((AccessorC03PacketPlayer) packet).setOnGround(false);
        }
        */
        HeadRotation.onPacket(packet);
    }
}