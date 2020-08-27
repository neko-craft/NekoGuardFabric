package cn.apisium.nekoguard.fabric.mixin.block.spread;

import cn.apisium.nekoguard.fabric.callback.BlockSpreadCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyBlock;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(SpreadableBlock.class)
public abstract class MixinSpreadableBlock extends SnowyBlock {

    public MixinSpreadableBlock(Settings settings) {
        super(settings);
    }

    @Shadow
    private static boolean canSurvive(BlockState state, WorldView worldView, BlockPos pos) {
        return false;
    }

    // BlockDirtSnowSpreadable 50
    @Inject(method = "randomTick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z",
                    ordinal = 1),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void onBlockSpread(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci,
                               BlockState blockState, int i, BlockPos blockPos) {
        BlockSpreadCallback.EVENT.invoker().interact(world, pos, blockPos,
                blockState.with(SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)), 2);
    }
}
