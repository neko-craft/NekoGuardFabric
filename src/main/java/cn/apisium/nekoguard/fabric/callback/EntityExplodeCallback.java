package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;

import java.util.List;

public interface EntityExplodeCallback {
    Event<EntityExplodeCallback> EVENT = EventFactory.createArrayBacked(EntityExplodeCallback.class,
            (listeners) -> (blockBreak) -> {
                for (EntityExplodeCallback event : listeners) {
                    event.interact(blockBreak);
                }
            });
    void interact(EntityExplode event);

    class EntityExplode{
        private final Entity entity;
        private final List<Block> blocks;

        public EntityExplode(Entity entity, List<Block> blocks) {
            this.entity = entity;
            this.blocks = blocks;
        }

        public List<Block> getBlocks() {
            return blocks;
        }

        public Entity getEntity() {
            return entity;
        }
    }
}
