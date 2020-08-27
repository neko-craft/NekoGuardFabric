package cn.apisium.nekoguard.fabric.mixin.block.spread;

import cn.apisium.nekoguard.fabric.callback.BlockSpreadCallback;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(FireBlock.class)
public abstract class MixinFireBlock extends AbstractFireBlock {

    public MixinFireBlock(Settings settings, float damage) {
        super(settings, damage);
    }
    // BlockFire 203
    @Redirect(method = "scheduledTick",
            at=@At(value = "INVOKE",
                    target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z",
                    ordinal = 1))
    private boolean redirectSetBlockState(World world, BlockPos pos, BlockState state, int flag) {
        BlockSpreadCallback.EVENT.invoker().interact(world, pos.down(), pos, state, flag);
        return world.setBlockState(pos, state, flag);
    }
}
