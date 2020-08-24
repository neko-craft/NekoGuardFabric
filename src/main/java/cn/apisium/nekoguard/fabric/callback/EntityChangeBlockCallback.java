package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;

public interface EntityChangeBlockCallback {
    Event<EntityChangeBlockCallback> EVENT = EventFactory.createArrayBacked(EntityChangeBlockCallback.class,
            (listeners) -> (entity, block) -> {
                for (EntityChangeBlockCallback event : listeners) {
                    event.interact(entity, block);
                }
            });
    void interact(Entity entity, Block block );
}
