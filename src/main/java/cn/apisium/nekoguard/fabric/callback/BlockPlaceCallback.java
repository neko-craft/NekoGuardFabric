package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;

public interface BlockPlaceCallback {
    Event<BlockPlaceCallback> EVENT = EventFactory.createArrayBacked(BlockPlaceCallback.class,
            (listeners) -> (playerEntity, block) -> {
                for (BlockPlaceCallback event : listeners) {
                    event.interact(playerEntity, block);
                }
    });
    void interact(PlayerEntity playerEntity, Block block );
}
