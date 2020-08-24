package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;

import java.util.List;

public interface BlockExplodeCallback {
    Event<BlockExplodeCallback> EVENT = EventFactory.createArrayBacked(BlockExplodeCallback.class,
            (listeners) -> (block, blocks) -> {
                for (BlockExplodeCallback event : listeners) {
                    event.interact(block, blocks);
                }
            });
    void interact( Block block, List<Block> blocks);
}