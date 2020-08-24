package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerCommandPreprocessCallback {
    Event<PlayerCommandPreprocessCallback> EVENT = EventFactory.createArrayBacked(PlayerCommandPreprocessCallback.class,
            (listeners) -> (playerEntity, message) -> {
                for (PlayerCommandPreprocessCallback event : listeners) {
                    event.interact(playerEntity, message);
                }
            });
    void interact(PlayerEntity playerEntity, String message );
}
