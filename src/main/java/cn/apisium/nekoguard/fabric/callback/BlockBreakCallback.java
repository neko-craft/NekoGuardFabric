package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;

public interface BlockBreakCallback {
    Event<BlockBreakCallback> EVENT = EventFactory.createArrayBacked(BlockBreakCallback.class,
            (listeners) -> (blockBreak) -> {
                for (BlockBreakCallback event : listeners) {
                    event.interact(blockBreak);
                }
            });
    void interact(BlockBreak event);

    class BlockBreak{
        private final PlayerEntity playerEntity;
        private final Block block;

        public BlockBreak(PlayerEntity playerEntity, Block block) {
            this.playerEntity = playerEntity;
            this.block = block;
        }

        public Block getBlock() {
            return block;
        }

        public PlayerEntity getPlayerEntity() {
            return playerEntity;
        }
    }
}
