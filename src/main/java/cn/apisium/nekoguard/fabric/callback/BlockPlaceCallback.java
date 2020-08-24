package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;

public interface BlockPlaceCallback {
    Event<BlockPlaceCallback> EVENT = EventFactory.createArrayBacked(BlockPlaceCallback.class,
            (listeners) -> (blockPlace) -> {
                for (BlockPlaceCallback event : listeners) {
                    event.interact(blockPlace);
                }
    });
    void interact(BlockPlace event);

    class BlockPlace{
        private final PlayerEntity playerEntity;
        private final Block block;

        public BlockPlace(PlayerEntity playerEntity, Block block) {
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
