package com.trytodupe.ttdaddons.Mixins;


import com.trytodupe.ttdaddons.Features.HeadRotation;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NetworkManager.class})
public class MixinNetworkManager {
    @Inject(method = {"channelRead0"}, at = {@At("HEAD")})
    private void channelRead0(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo) {
        HeadRotation.onPacket(packet);
    }

    @Inject(method = {"sendPacket(Lnet/minecraft/network/Packet;)V"}, at = {@At("HEAD")})
    private void sendPacket(Packet<?> packet, CallbackInfo callbackInfo) {
        HeadRotation.onPacket(packet);
    }
}