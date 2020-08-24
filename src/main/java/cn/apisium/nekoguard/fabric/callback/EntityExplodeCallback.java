package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;

import java.util.List;

public interface EntityExplodeCallback {
    Event<EntityExplodeCallback> EVENT = EventFactory.createArrayBacked(EntityExplodeCallback.class,
            (listeners) -> (entity, blocks) -> {
                for (EntityExplodeCallback event : listeners) {
                    event.interact(entity, blocks);
                }
            });
    void interact(Entity entity, List<Block> blocks);
}
