package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = {"net.minecraft.entity.mob.EndermanEntity$PickUpBlockGoal"})
public abstract class MixinEnderManEntity_PickUpBlockGoal_EntityChangeBlock {
    @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"))
    private boolean onEntityChangeBlock(World world, BlockPos pos, boolean move){
        PushHandler.getInstance().onEntityChangeBlock();
        return world.removeBlock(pos, move);
    }
}
