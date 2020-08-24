package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class MixinServerPlayNetworkHandler_PlayerCommandPreprocessAndPlayerChat {

    @Redirect(method = "onGameMessage", at = @At(value = "INVOKE", target = "Ljava/lang/String;startsWith(Ljava/lang/String;)Z"))
    private boolean onPlayerCommandPreprocessAndPlayerChat(String s, String prefix){
        boolean ret = s.startsWith(prefix);
        if (ret){
            PushHandler.getInstance().onPlayerCommand();
        } else {
            PushHandler.getInstance().onChat();
        }
        return false;
    }
}
