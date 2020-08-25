package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

public interface ServerCommandCallback {
    Event<ServerCommandCallback> EVENT = EventFactory.createArrayBacked(ServerCommandCallback.class,
            (listeners) -> (message) -> {
                for (ServerCommandCallback event : listeners) {
                    event.interact(message);
                }
            });

    /**
     * 服务器命令事件
     * @param message 命令
     */
    void interact(@NotNull String message );
}
