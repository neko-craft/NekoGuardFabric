package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerChatCallback {
    Event<PlayerChatCallback> EVENT = EventFactory.createArrayBacked(PlayerChatCallback.class,
            (listeners) -> (playerEntity, message) -> {
                for (PlayerChatCallback event : listeners) {
                    event.interact(playerEntity, message);
                }
            });
    void interact(PlayerEntity playerEntity, String message );
}
