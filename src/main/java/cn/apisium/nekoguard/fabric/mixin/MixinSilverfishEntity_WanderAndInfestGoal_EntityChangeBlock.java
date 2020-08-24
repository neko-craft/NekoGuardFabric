package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.EntityChangeBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.InfestedBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = {"net.minecraft.entity.mob.SilverfishEntity$WanderAndInfestGoal"})
public abstract class MixinSilverfishEntity_WanderAndInfestGoal_EntityChangeBlock {
    @Redirect(method = "start()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/InfestedBlock;isInfestable(Lnet/minecraft/block/BlockState;)Z"))
    private boolean onEntityChangeBlock(BlockState block){
        boolean ret = InfestedBlock.isInfestable(block);
        if(ret){
            EntityChangeBlockCallback.EVENT.invoker().interact(null, block.getBlock());
        }
        return ret;
    }
}
