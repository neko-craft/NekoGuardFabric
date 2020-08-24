package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;

public interface EntityDeathCallback {
    Event<EntityDeathCallback> EVENT = EventFactory.createArrayBacked(EntityDeathCallback.class,
            (listeners) -> (entity) -> {
                for (EntityDeathCallback event : listeners) {
                    event.interact(entity);
                }
            });
    void interact(Entity entity);
}
