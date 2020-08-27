package cn.apisium.nekoguard.fabric.mixin.block.burn;

import cn.apisium.nekoguard.fabric.callback.BlockBurnCallback;
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
public abstract class MixinFireBlock {
    private BlockPos source;
    // 添加这个注入来获取 burn 来源
    @Inject(method = "scheduledTick",
            at=@At(value = "INVOKE",
                    target = "Lnet/minecraft/block/FireBlock;trySpreadingFire(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;ILjava/util/Random;I)V",
                    ordinal = 0))
    private void onScheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        this.source = pos;
    }

    // BlockFire 261
    @Redirect(method = "trySpreadingFire",
            at = @At(value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;",
                    ordinal = 1))
    private BlockState onBlockBurn(World world, BlockPos pos){
        BlockBurnCallback.EVENT.invoker().interact(world, this.source, pos);
        return world.getBlockState(pos);
    }
}
