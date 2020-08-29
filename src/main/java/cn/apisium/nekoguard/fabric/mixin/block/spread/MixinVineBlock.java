package cn.apisium.nekoguard.fabric.mixin.block.spread;

import cn.apisium.nekoguard.fabric.callback.BlockSpreadCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Random;


@Mixin(VineBlock.class)
public abstract class MixinVineBlock extends Block {

    public MixinVineBlock(Settings settings) {
        super(settings);
    }

    // 可能用 redirect 来实现会更优雅

    // BlockVine 184
    @Inject(method = "randomTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z",
                    ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void onRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci,
                              Direction direction, BlockPos blockPos, BlockPos blockPos2, BlockState blockState3,
                              Direction direction5, Direction direction3, boolean bl, boolean bl2,
                              BlockPos blockPos3, BlockPos blockPos4) {
        BlockSpreadCallback.EVENT.invoker().interact(world, pos, blockPos2,
                this.getDefaultState().with(VineBlock.getFacingProperty(direction5), true), 2);
    }

    // BlockVine 186
    @Inject(method = "randomTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z",
                    ordinal = 1),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void onRandomTick2(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci,
                               Direction direction, BlockPos blockPos, BlockPos blockPos2, BlockState blockState3,
                               Direction direction5, Direction direction3, boolean bl, boolean bl2,
                               BlockPos blockPos3, BlockPos blockPos4) {
        BlockSpreadCallback.EVENT.invoker().interact(world, pos, blockPos2,
                this.getDefaultState().with(VineBlock.getFacingProperty(direction3), true), 2);
    }

    // BlockVine 191
    @Inject(method = "randomTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z",
                    ordinal = 2),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void onRandomTick3(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci,
                               Direction direction, BlockPos blockPos, BlockPos blockPos2, BlockState blockState3,
                               Direction direction5, Direction direction3, boolean bl, boolean bl2,
                               BlockPos blockPos3, BlockPos blockPos4, Direction direction4) {
        BlockSpreadCallback.EVENT.invoker().interact(world, pos, blockPos3,
                this.getDefaultState().with(VineBlock.getFacingProperty(direction4), true), 2);
    }

    // BlockVine 193
    @Inject(method = "randomTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z",
                    ordinal = 3),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void onRandomTick4(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci,
                               Direction direction, BlockPos blockPos, BlockPos blockPos2, BlockState blockState3,
                               Direction direction5, Direction direction3, boolean bl, boolean bl2,
                               BlockPos blockPos3, BlockPos blockPos4, Direction direction4) {
        BlockSpreadCallback.EVENT.invoker().interact(world, pos, blockPos4,
                this.getDefaultState().with(VineBlock.getFacingProperty(direction4), true), 2);
    }

    // BlockVine 195
    @Inject(method = "randomTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z",
                    ordinal = 4),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void onRandomTick5(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci,
                               Direction direction, BlockPos blockPos, BlockPos blockPos2, BlockState blockState3,
                               Direction direction5, Direction direction3, boolean bl, boolean bl2,
                               BlockPos blockPos3, BlockPos blockPos4) {
        BlockSpreadCallback.EVENT.invoker().interact(world, pos, blockPos2,
                this.getDefaultState().with(VineBlock.UP, true), 2);
    }

    // BlockVine 227
    // LVT 可能有误
    @Inject(method = "randomTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z",
                    ordinal = 7),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void onRandomTick6(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci,
                               Direction direction, BlockPos blockPos, BlockState blockState2) {
        BlockSpreadCallback.EVENT.invoker().interact(world, pos, blockPos, blockState2, 2);
    }

    // BlockVine 242
    @Inject(method = "randomTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z",
                    ordinal = 8),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void onRandomTick7(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci,
                               Direction direction, BlockPos blockPos, BlockPos blockPos2, BlockState blockState3,
                               BlockState blockState4, BlockState blockState5) {
        BlockSpreadCallback.EVENT.invoker().interact(world, pos, blockPos2, blockState5, 2);
    }
}
