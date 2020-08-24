package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

public interface InventoryClickCallback {
    Event<InventoryClickCallback> EVENT = EventFactory.createArrayBacked(InventoryClickCallback.class,
            (listeners) -> (playerEntity) -> {
                for (InventoryClickCallback event : listeners) {
                    event.interact(playerEntity);
                }
            });
    void interact(PlayerEntity playerEntity);
}
