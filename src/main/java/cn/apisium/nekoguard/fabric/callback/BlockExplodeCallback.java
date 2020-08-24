package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;

import java.util.List;

public interface BlockExplodeCallback {
    Event<BlockExplodeCallback> EVENT = EventFactory.createArrayBacked(BlockExplodeCallback.class,
            (listeners) -> (blockExplode) -> {
                for (BlockExplodeCallback event : listeners) {
                    event.interact(blockExplode);
                }
            });
    void interact(BlockExplode event);

    class BlockExplode{
        private final Block block;
        private final List<Block> blocks;

        public BlockExplode(Block block, List<Block> blocks) {
            this.block = block;
            this.blocks = blocks;
        }

        public Block getBlock() {
            return block;
        }

        public List<Block> getBlocks() {
            return blocks;
        }
    }
}