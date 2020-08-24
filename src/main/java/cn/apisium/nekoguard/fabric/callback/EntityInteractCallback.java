package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public interface EntityInteractCallback {
    Event<EntityInteractCallback> EVENT = EventFactory.createArrayBacked(EntityInteractCallback.class,
            (listeners) -> (entity) -> {
                for (EntityInteractCallback event : listeners) {
                    event.interact(entity);
                }
            });
    void interact(Entity entity);
}
