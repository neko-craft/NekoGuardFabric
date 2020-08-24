package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.BlockBreakCallback;
import net.minecraft.block.Block;
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
public abstract class MixinServerPlayerInteractionManager_PlayerBlockBreak {

    @Shadow public ServerPlayerEntity player;

    @Shadow public ServerWorld world;

    @Redirect(method = "tryBreakBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"))
    private BlockState onPlayerBlockBreak(ServerWorld serverWorld, BlockPos pos){
        BlockState ret = serverWorld.getBlockState(pos);
        BlockBreakCallback.EVENT.invoker().interact(player, ret,world, pos);
        return ret;
    }
}
