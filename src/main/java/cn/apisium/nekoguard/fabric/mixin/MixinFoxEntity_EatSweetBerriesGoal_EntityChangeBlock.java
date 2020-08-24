package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = {"net.minecraft.entity.passive.FoxEntity$EatSweetBerriesGoal"})
public abstract class MixinFoxEntity_EatSweetBerriesGoal_EntityChangeBlock {

    @Redirect(method = "eatSweetBerry()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private boolean onEntityChangeBlock(BlockState blockState, Block block){
        boolean ret = blockState.isOf(block);
        if(ret){
            PushHandler.getInstance().onEntityChangeBlock();
        }
        return ret;
    }
}
