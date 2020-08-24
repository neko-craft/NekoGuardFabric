package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;

public interface BlockBurnCallback {
    Event<BlockBurnCallback> EVENT = EventFactory.createArrayBacked(BlockBurnCallback.class,
            (listeners) -> (block) -> {
                for (BlockBurnCallback event : listeners) {
                    event.interact(block);
                }
            });
    void interact(Block block);
}
