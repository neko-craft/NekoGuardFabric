package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(BambooBlock.class)
public abstract class MixinBambooBlock_BlockSpread {

    @Inject(method = "updateLeaves", at = @At("RETURN"))
    private void onBlockSpread(BlockState state, World world, BlockPos pos, Random random, int height, CallbackInfo ci){
        PushHandler.getInstance().onBlockSpread();
    }
}
