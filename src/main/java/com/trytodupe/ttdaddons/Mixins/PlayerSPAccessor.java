package com.trytodupe.ttdaddons.Mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({EntityPlayerSP.class})
public interface PlayerSPAccessor {
    @Accessor
    float getLastReportedYaw();

    @Accessor
    void setLastReportedYaw(float paramFloat);

    @Accessor
    float getLastReportedPitch();

    @Accessor
    void setLastReportedPitch(float paramFloat);
}
