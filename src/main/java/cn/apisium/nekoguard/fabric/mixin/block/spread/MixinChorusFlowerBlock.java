package cn.apisium.nekoguard.fabric.mixin.block.spread;

import cn.apisium.nekoguard.fabric.callback.BlockSpreadCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(ChorusFlowerBlock.class)
public abstract class MixinChorusFlowerBlock extends Block {

    public MixinChorusFlowerBlock(Settings settings) {
        super(settings);
    }

    @Shadow
    protected abstract void grow(World world, BlockPos pos, int age);

    // 由于生长方向可能为向上或者水平，因此需要分别对两次 grow 进行 mixin，从而确定生长来源
    // BlockChorusFlower 74
    @Redirect(method = "randomTick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/block/ChorusFlowerBlock;grow(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;I)V",
                    ordinal = 0))
    private void redirectGrow(ChorusFlowerBlock chorusFlowerBlock, World world, BlockPos pos, int age) {
        BlockSpreadCallback.EVENT.invoker().interact(world, pos.down(), pos,
                this.getDefaultState().with(ChorusFlowerBlock.AGE, age), 2);
        this.grow(world, pos, age);
    }

    // 随机生长方向，需要捕获局部变量
    // 很可能会有兼容性问题....
    // BlockChorusFlower 93
    @Inject(method = "randomTick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/block/ChorusFlowerBlock;grow(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;I)V",
                    ordinal = 1),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void onRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci,
                              BlockPos blockPos, int i, boolean bl, boolean bl2,
                              BlockState blockState, Block block, int l,
                              boolean bl3, int m, Direction direction, BlockPos blockPos2) {
        BlockSpreadCallback.EVENT.invoker().interact(world, pos, blockPos2,
                this.getDefaultState().with(ChorusFlowerBlock.AGE, i + 1), 2);
    }
}
