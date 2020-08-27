package cn.apisium.nekoguard.fabric.mixin.block.spread;

import cn.apisium.nekoguard.fabric.callback.BlockSpreadCallback;
import net.minecraft.block.AbstractPlantPartBlock;
import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractPlantStemBlock.class)
public abstract class MixinAbstractPlantStemBlock extends AbstractPlantPartBlock implements Fertilizable {

    public MixinAbstractPlantStemBlock(Settings settings, Direction growthDirection, VoxelShape outlineShape, boolean tickWater) {
        super(settings, growthDirection, outlineShape, tickWater);
    }

    @Redirect(method = "randomTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z",
                    ordinal = 0))
    private boolean onBlockSpread(ServerWorld world, BlockPos pos, BlockState state) {
        BlockPos source = pos.add(
                -this.growthDirection.getOffsetX(),
                -this.growthDirection.getOffsetY(),
                -this.growthDirection.getOffsetZ());
        BlockSpreadCallback.EVENT.invoker().interact(world, source, pos, state, 2);
        return world.setBlockState(pos, state);
    }
}
