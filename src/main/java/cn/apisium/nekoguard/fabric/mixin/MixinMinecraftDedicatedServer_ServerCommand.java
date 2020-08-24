package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.ServerCommandCallback;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(MinecraftDedicatedServer.class)
public abstract class MixinMinecraftDedicatedServer_ServerCommand {

    @Redirect(method = "executeQueuedCommands", at = @At(value = "INVOKE", target = "Ljava/util/List;remove(I)Ljava/lang/Object;"))
    private <E> E onServerCommand(List<E> list, int index){
        E ret = list.remove(index);
        ServerCommandCallback.EVENT.invoker().interact(ret.toString());
        return ret;
    }
}
