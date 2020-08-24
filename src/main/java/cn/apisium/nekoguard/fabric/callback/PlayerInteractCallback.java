package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerInteractCallback {
    Event<PlayerInteractCallback> EVENT = EventFactory.createArrayBacked(PlayerInteractCallback.class,
            (listeners) -> (playerEntity) -> {
                for (PlayerInteractCallback event : listeners) {
                    event.interact(playerEntity);
                }
            });
    void interact(PlayerEntity playerEntity);
}
