package cn.apisium.nekoguard.fabric.mixin.entity.change.block;

import cn.apisium.nekoguard.fabric.callback.EntityChangeBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EatGrassGoal.class)
public abstract class MixinEatGrassGoal extends Goal {

    @Shadow
    @Final
    private MobEntity mob;

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;breakBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"))
    private boolean redirectBreakBlock(World world, BlockPos pos, boolean drop) {
        EntityChangeBlockCallback.EVENT.invoker().interact(mob, Blocks.AIR.getDefaultState(), pos);
        return world.breakBlock(pos, drop);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private boolean redirectSetBlockState(World world, BlockPos pos, BlockState state, int flags) {
        EntityChangeBlockCallback.EVENT.invoker().interact(mob, state, pos);
        return world.setBlockState(pos, state, flags);
    }
}
