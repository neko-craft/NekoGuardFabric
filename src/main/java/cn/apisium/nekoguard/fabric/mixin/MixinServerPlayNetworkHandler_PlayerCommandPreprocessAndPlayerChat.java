package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.PlayerChatCallback;
import cn.apisium.nekoguard.fabric.callback.PlayerCommandPreprocessCallback;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class MixinServerPlayNetworkHandler_PlayerCommandPreprocessAndPlayerChat {

    @Shadow public ServerPlayerEntity player;

    @Redirect(method = "onGameMessage", at = @At(value = "INVOKE", target = "Ljava/lang/String;startsWith(Ljava/lang/String;)Z"))
    private boolean onPlayerCommandPreprocessAndPlayerChat(String s, String prefix){
        boolean ret = s.startsWith(prefix);
        if (ret){
            PlayerCommandPreprocessCallback.EVENT.invoker().interact(player, s);
        } else {
            PlayerChatCallback.EVENT.invoker().interact(player, s);
        }
        return false;
    }
}
