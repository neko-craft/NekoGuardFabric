package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.BlockFromToCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DragonEggBlock.class)
public abstract class MixinDragonEggBlock_BlockFromTo {

    @Redirect(method = "teleport", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isAir()Z"))
    private boolean onBlockFromTo(BlockState blockState, BlockState state, World world, BlockPos pos){
        boolean ret = blockState.isAir();
        if(ret){
            BlockFromToCallback.EVENT.invoker().interact(world.getBlockState(pos).getBlock());
        }
        return ret;
    }
}
