package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.BlockFormCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ConcretePowderBlock.class)
public abstract class MixinConcretePowderBlock_BlockForm {

    @Shadow
    private static boolean hardensOnAnySide(BlockView world, BlockPos pos) {
        return false;
    }

    @Redirect(method = "onLanding", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private boolean onBlockForm(World world, BlockPos pos, BlockState state, int flags){
        BlockFormCallback.EVENT.invoker().interact(world.getBlockState(pos).getBlock());
        return world.setBlockState(pos, state, flags);
    }

    @Inject(method = "getPlacementState", at = @At("HEAD"))
    private void onBlockForm(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir){
        BlockFormCallback.EVENT.invoker().interact(ctx.getWorld().getBlockState(ctx.getBlockPos()).getBlock());
    }

    @Inject(method = "getStateForNeighborUpdate", at = @At("HEAD"))
    private void onBlockForm(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom, CallbackInfoReturnable<BlockState> cir){
        if (hardensOnAnySide(world, pos)){
            BlockFormCallback.EVENT.invoker().interact(world.getBlockState(pos).getBlock());
        }
    }
}
