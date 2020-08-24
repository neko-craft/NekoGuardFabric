package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerBucketEmptyCallback {
    Event<PlayerBucketEmptyCallback> EVENT = EventFactory.createArrayBacked(PlayerBucketEmptyCallback.class,
            (listeners) -> (playerEntity) -> {
                for (PlayerBucketEmptyCallback event : listeners) {
                    event.interact(playerEntity);
                }
            });
    void interact(PlayerEntity playerEntity);
}
