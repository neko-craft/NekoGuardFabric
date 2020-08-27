package cn.apisium.nekoguard.fabric.mixin.block.spread;

import cn.apisium.nekoguard.fabric.callback.BlockSpreadCallback;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BambooBlock.class)
public abstract class MixinBambooBlock extends Block implements Fertilizable {
    public MixinBambooBlock() {
        super(null);
    }

    // BlockBamboo 194
    @Redirect(method = "updateLeaves",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z",
                    ordinal = 2))
    private boolean redirectSetBlockState(World world, BlockPos pos, BlockState state, int flag) {
        BlockSpreadCallback.EVENT.invoker().interact(world, pos.down(), pos, state, flag);
        return world.setBlockState(pos, state, flag);
    }
}
