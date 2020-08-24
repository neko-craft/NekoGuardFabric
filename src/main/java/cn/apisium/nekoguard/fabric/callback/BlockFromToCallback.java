package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;

public interface BlockFromToCallback {
    Event<BlockFromToCallback> EVENT = EventFactory.createArrayBacked(BlockFromToCallback.class,
            (listeners) -> (block) -> {
                for (BlockFromToCallback event : listeners) {
                    event.interact(block);
                }
            });
    void interact(Block block);
}
