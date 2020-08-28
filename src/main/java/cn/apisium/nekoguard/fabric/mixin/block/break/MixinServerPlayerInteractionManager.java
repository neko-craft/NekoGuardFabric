package cn.apisium.nekoguard.fabric.mixin.player.breakblock;

import cn.apisium.nekoguard.fabric.callback.BlockBreakCallback;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class MixinServerPlayerInteractionManager {

    @Shadow public ServerPlayerEntity player;

    @Shadow public ServerWorld world;

    @Redirect(method = "tryBreakBlock",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;",
                    ordinal = 0))
    private BlockState onPlayerBlockBreak(ServerWorld serverWorld, BlockPos pos){
        BlockState ret = serverWorld.getBlockState(pos);
        BlockBreakCallback.EVENT.invoker().interact(player, ret,world, pos);
        return ret;
    }
}
