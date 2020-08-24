package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FlowableFluid.class)
public abstract class MixinFlowableFluid_BlockFromTo {

    @Shadow protected abstract void flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState);

    @Redirect(method = "tryFlow", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FlowableFluid;flow(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;Lnet/minecraft/fluid/FluidState;)V"))
    private void onBlockFromTo1(FlowableFluid flowableFluid, WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState){
        PushHandler.getInstance().onBlockFromTo();
        flow(world, pos, state, direction, fluidState);
    }

    @Redirect(method = "method_15744", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FlowableFluid;flow(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;Lnet/minecraft/fluid/FluidState;)V"))
    private void onBlockFromTo2(FlowableFluid flowableFluid, WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState){
        PushHandler.getInstance().onBlockFromTo();
        flow(world, pos, state, direction, fluidState);
    }
}