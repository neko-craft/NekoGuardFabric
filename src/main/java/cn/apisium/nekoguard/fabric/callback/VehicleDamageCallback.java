package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface VehicleDamageCallback {
    Event<VehicleDamageCallback> EVENT = EventFactory.createArrayBacked(VehicleDamageCallback.class,
            (listeners) -> (vehicle, source, damage) -> {
                for (VehicleDamageCallback event : listeners) {
                    event.interact(vehicle, source, damage);
                }
            });

    /**
     * 载具受伤事件
     * @param vehicle 载具
     * @param source 攻击者
     * @param damage 伤害量
     */
    void interact(@NotNull Entity vehicle, @NotNull DamageSource source, double damage);
}
