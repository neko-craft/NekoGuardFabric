package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.DragonEggBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DragonEggBlock.class)
public abstract class MixinDragonEggBlock_BlockFromTo {

    @Redirect(method = "teleport", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isAir()Z"))
    private boolean onBlockFromTo(BlockState blockState){
        boolean ret = blockState.isAir();
        if(ret){
            PushHandler.getInstance().onBlockFromTo();
        }
        return ret;
    }
}
