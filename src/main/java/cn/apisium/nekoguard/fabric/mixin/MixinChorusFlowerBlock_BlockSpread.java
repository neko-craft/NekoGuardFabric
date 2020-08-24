package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChorusFlowerBlock.class)
public abstract class MixinChorusFlowerBlock_BlockSpread {

    @Shadow protected abstract void grow(World world, BlockPos pos, int age);

    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/ChorusFlowerBlock;grow(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;I)V"))
    private void onBlockSpread(ChorusFlowerBlock chorusFlowerBlock, World world, BlockPos pos, int age){
        PushHandler.getInstance().onBlockSpread();
        grow(world, pos, age);
    }
}
