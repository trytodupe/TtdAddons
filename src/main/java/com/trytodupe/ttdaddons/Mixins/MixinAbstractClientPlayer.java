package com.trytodupe.ttdaddons.Mixins;

import net.minecraft.client.entity.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({AbstractClientPlayer.class})
public abstract class MixinAbstractClientPlayer extends MixinPlayer {

}
