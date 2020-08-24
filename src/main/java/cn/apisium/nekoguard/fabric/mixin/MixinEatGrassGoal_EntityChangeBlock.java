package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.EntityChangeBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EatGrassGoal.class)
public abstract class MixinEatGrassGoal_EntityChangeBlock {

    @Shadow @Final private MobEntity mob;

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;breakBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"))
    private boolean onEntityChangeBlock(World world, BlockPos pos, boolean drop){
        EntityChangeBlockCallback.EVENT.invoker().interact(mob, world.getBlockState(pos).getBlock());
        return world.breakBlock(pos, drop);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private boolean onEntityChangeBlock(World world, BlockPos pos, BlockState state, int flags){
        EntityChangeBlockCallback.EVENT.invoker().interact(mob, state.getBlock());
        return world.setBlockState(pos, state, flags);
    }
}
