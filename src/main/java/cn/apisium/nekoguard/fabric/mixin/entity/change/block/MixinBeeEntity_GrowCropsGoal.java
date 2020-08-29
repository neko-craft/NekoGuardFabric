package cn.apisium.nekoguard.fabric.mixin.entity.change.block;

import cn.apisium.nekoguard.fabric.callback.EntityChangeBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = {"net.minecraft.entity.passive.BeeEntity$GrowCropsGoal"})
public abstract class MixinBeeEntity_GrowCropsGoal extends Goal {
    // BeeEntity.this
    @Final
    @Shadow(aliases = "this$0")
    BeeEntity field_20373;

    @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"))
    private boolean onEntityChangeBlock(World world, BlockPos pos, BlockState state) {
        EntityChangeBlockCallback.EVENT.invoker().interact(field_20373, world.getBlockState(pos), pos);
        return world.setBlockState(pos, state);
    }
}