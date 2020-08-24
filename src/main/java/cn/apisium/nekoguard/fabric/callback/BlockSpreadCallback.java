package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;

public interface BlockSpreadCallback {
    Event<BlockSpreadCallback> EVENT = EventFactory.createArrayBacked(BlockSpreadCallback.class,
            (listeners) -> (block) -> {
                for (BlockSpreadCallback event : listeners) {
                    event.interact(block);
                }
            });
    void interact(Block block);
}
