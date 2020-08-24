package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerJoinCallback {
    Event<PlayerJoinCallback> EVENT = EventFactory.createArrayBacked(PlayerJoinCallback.class,
            (listeners) -> (playerEntity) -> {
                for (PlayerJoinCallback event : listeners) {
                    event.interact(playerEntity);
                }
            });
    void interact(PlayerEntity playerEntity);
}
