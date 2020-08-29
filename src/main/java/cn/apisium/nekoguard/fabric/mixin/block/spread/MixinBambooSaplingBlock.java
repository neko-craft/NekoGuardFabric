package cn.apisium.nekoguard.fabric.mixin.block.spread;

import cn.apisium.nekoguard.fabric.callback.BlockSpreadCallback;
import net.minecraft.block.BambooSaplingBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BambooSaplingBlock.class)
public abstract class MixinBambooSaplingBlock {

    // BlockBambooSapling 72
    @Redirect(method = "grow(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z",
                    ordinal = 0))
    private boolean redirectSetBlockState(World world, BlockPos pos, BlockState state, int flag) {
        BlockSpreadCallback.EVENT.invoker().interact(world, pos.down(), pos, state, flag);
        return world.setBlockState(pos, state, flag);
    }
}
