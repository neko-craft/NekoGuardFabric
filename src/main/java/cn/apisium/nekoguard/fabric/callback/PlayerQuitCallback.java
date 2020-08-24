package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerQuitCallback {
    Event<PlayerQuitCallback> EVENT = EventFactory.createArrayBacked(PlayerQuitCallback.class,
            (listeners) -> (playerEntity) -> {
                for (PlayerQuitCallback event : listeners) {
                    event.interact(playerEntity);
                }
            });
    void interact(PlayerEntity playerEntity);
}
