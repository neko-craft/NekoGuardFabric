package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

public interface EntityPickupItemCallback {
    Event<EntityPickupItemCallback> EVENT = EventFactory.createArrayBacked(EntityPickupItemCallback.class,
            (listeners) -> (playerEntity) -> {
                for (EntityPickupItemCallback event : listeners) {
                    event.interact(playerEntity);
                }
            });
    void interact(PlayerEntity playerEntity);
}
