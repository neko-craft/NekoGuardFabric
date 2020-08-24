package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerBucketFillCallback {
    Event<PlayerBucketFillCallback> EVENT = EventFactory.createArrayBacked(PlayerBucketFillCallback.class,
            (listeners) -> (playerEntity, block) -> {
                for (PlayerBucketFillCallback event : listeners) {
                    event.interact(playerEntity, block);
                }
            });
    void interact(PlayerEntity playerEntity, Block block );
}
