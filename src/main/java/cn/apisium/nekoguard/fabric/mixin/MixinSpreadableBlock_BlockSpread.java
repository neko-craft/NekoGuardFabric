package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SpreadableBlock.class)
public abstract class MixinSpreadableBlock_BlockSpread {

    @Shadow
    protected static boolean canSurvive(BlockState state, WorldView worldView, BlockPos pos) {
        return false;
    }

    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/SpreadableBlock;canSurvive(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z"))
    private boolean onBlockSpread(BlockState state, WorldView worldView, BlockPos pos){
        boolean ret = canSurvive(state, worldView, pos);
        if(!ret){
            PushHandler.getInstance().onBlockSpread();
        }

        return ret;
    }

    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"))
    private  <T extends Comparable<T>, V extends T,S>  S onBlockSpread(BlockState blockState, Property<T> property, V value){
        PushHandler.getInstance().onBlockSpread();
        return (S) blockState.with(property, value);
    }
}
