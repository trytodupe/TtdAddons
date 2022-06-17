package com.trytodupe.ttdaddons.Mixins;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({PlayerControllerMP.class})
public class MixinPlayerControllerMP {

    @Shadow
    private int blockHitDelay;

    @Redirect(method = {"onPlayerDamageBlock"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getPlayerRelativeBlockHardness(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)F"))
    public float onPlayerDamageBlock(Block instance, EntityPlayer playerIn, World worldIn, BlockPos pos) {
        float hardness = instance.getPlayerRelativeBlockHardness(playerIn, worldIn, pos);
        if (ConfigHandler.speedMine)
            hardness = (float) (hardness * ConfigHandler.speedMineSpeed > 0.8 ? 1 : hardness * ConfigHandler.speedMineSpeed);
        return hardness;
    }

    @Inject(method = {"onPlayerDamageBlock"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;blockHitDelay:I"))
    public void blockHitDelay(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir) {
        if (ConfigHandler.speedMine) blockHitDelay = 0;
    }
}
