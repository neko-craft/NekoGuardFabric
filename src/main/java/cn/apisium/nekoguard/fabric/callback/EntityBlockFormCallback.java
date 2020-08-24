package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public interface EntityBlockFormCallback {
    Event<EntityBlockFormCallback> EVENT = EventFactory.createArrayBacked(EntityBlockFormCallback.class,
            (listeners) -> (entity, block) -> {
                for (EntityBlockFormCallback event : listeners) {
                    event.interact(entity, block);
                }
            });
    void interact(Entity entity, Block block );
}
