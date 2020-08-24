package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;

public interface BlockBreakCallback {
    Event<BlockBreakCallback> EVENT = EventFactory.createArrayBacked(BlockBreakCallback.class,
            (listeners) -> (playerEntity, block) -> {
                for (BlockBreakCallback event : listeners) {
                    event.interact(playerEntity, block);
                }
            });
    void interact(PlayerEntity playerEntity, Block block);
}
