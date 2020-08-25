package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.InventoryClickCallback;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class MixinServerPlayNetworkHandler_PlayerInventoryClick {
    @Shadow public ServerPlayerEntity player;

    @Redirect(method = "onClickWindow", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;isSpectator()Z"))
    private boolean onPlayerInventoryClick(ServerPlayerEntity serverPlayerEntity){
        InventoryClickCallback.EVENT.invoker().interact(player);
        return serverPlayerEntity.isSpectator();
    }
}
