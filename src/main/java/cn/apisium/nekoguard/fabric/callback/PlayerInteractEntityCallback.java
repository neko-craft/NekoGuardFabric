package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerInteractEntityCallback {
    Event<PlayerInteractEntityCallback> EVENT = EventFactory.createArrayBacked(PlayerInteractEntityCallback.class,
            (listeners) -> (playerEntity) -> {
                for (PlayerInteractEntityCallback event : listeners) {
                    event.interact(playerEntity);
                }
            });
    void interact(PlayerEntity playerEntity);
}
