package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;

public interface BlockFormCallback {
    Event<BlockFormCallback> EVENT = EventFactory.createArrayBacked(BlockFormCallback.class,
            (listeners) -> (block) -> {
                for (BlockFormCallback event : listeners) {
                    event.interact(block);
                }
            });
    void interact(Block block);
}
