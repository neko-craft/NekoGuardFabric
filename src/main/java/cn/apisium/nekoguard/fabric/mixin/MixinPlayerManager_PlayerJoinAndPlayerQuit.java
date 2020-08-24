package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.PlayerJoinCallback;
import cn.apisium.nekoguard.fabric.callback.PlayerQuitCallback;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public abstract class MixinPlayerManager_PlayerJoinAndPlayerQuit {

    @Inject(method = "onPlayerConnect", at = @At("HEAD"))
    private void onPlayerJoin(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci){
        PlayerJoinCallback.EVENT.invoker().interact(player);
    }

    @Inject(method = "remove", at = @At("RETURN"))
    private void onPlayerQuit(ServerPlayerEntity player, CallbackInfo ci){
        PlayerQuitCallback.EVENT.invoker().interact(player);
    }
}
