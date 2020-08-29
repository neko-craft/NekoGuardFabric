package cn.apisium.nekoguard.fabric.mixin.block.spread;

import cn.apisium.nekoguard.fabric.callback.BlockSpreadCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(MushroomPlantBlock.class)
public abstract class MixinMushroomPlantBlock extends PlantBlock implements Fertilizable {
    public MixinMushroomPlantBlock(Settings settings) {
        super(settings);
    }

    // BlockMushroom 52
    // 为了获取来源，需要使用inject 捕获局部变量
    @Inject(method = "randomTick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z",
                    ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void onRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci,
                              int i, int j, BlockPos blockPos2) {
        BlockSpreadCallback.EVENT.invoker().interact(world, pos, blockPos2, state, 2);
    }
}
