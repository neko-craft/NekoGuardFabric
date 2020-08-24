package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;

public interface VehicleDamageCallback {
    Event<VehicleDamageCallback> EVENT = EventFactory.createArrayBacked(VehicleDamageCallback.class,
            (listeners) -> (entity) -> {
                for (VehicleDamageCallback event : listeners) {
                    event.interact(entity);
                }
            });
    void interact(Entity entity);
}
