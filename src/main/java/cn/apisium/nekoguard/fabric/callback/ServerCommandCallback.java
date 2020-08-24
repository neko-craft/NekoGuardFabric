package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

public interface ServerCommandCallback {
    Event<ServerCommandCallback> EVENT = EventFactory.createArrayBacked(ServerCommandCallback.class,
            (listeners) -> (message) -> {
                for (ServerCommandCallback event : listeners) {
                    event.interact(message);
                }
            });
    void interact(String message );
}
