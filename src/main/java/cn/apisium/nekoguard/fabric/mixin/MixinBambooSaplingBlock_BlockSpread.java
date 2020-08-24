package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.BlockSpreadCallback;
import net.minecraft.block.BambooSaplingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BambooSaplingBlock.class)
public abstract class MixinBambooSaplingBlock_BlockSpread {

    @Inject(method = "grow(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V", at = @At("HEAD"))
    private void onBlockSpread(World world, BlockPos pos, CallbackInfo ci){
        BlockSpreadCallback.EVENT.invoker().interact(world.getBlockState(pos).getBlock());
    }
}
