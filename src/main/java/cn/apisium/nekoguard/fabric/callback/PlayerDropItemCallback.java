package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerDropItemCallback {
    Event<PlayerDropItemCallback> EVENT = EventFactory.createArrayBacked(PlayerDropItemCallback.class,
            (listeners) -> (playerEntity) -> {
                for (PlayerDropItemCallback event : listeners) {
                    event.interact(playerEntity);
                }
            });
    void interact(PlayerEntity playerEntity);
}
