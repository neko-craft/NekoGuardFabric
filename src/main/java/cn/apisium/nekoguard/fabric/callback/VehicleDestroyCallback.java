package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;

public interface VehicleDestroyCallback {
    Event<VehicleDestroyCallback> EVENT = EventFactory.createArrayBacked(VehicleDestroyCallback.class,
            (listeners) -> (entity) -> {
                for (VehicleDestroyCallback event : listeners) {
                    event.interact(entity);
                }
            });
    void interact(Entity entity);
}
