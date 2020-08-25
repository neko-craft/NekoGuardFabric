package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface VehicleDamageCallback {
    Event<VehicleDamageCallback> EVENT = EventFactory.createArrayBacked(VehicleDamageCallback.class,
            (listeners) -> (entity, damage, world, pos) -> {
                for (VehicleDamageCallback event : listeners) {
                    event.interact(entity, damage, world, pos);
                }
            });

    /**
     * 载具受伤事件
     * @param entity 载具
     * @param damage 攻击者
     * @param world 世界
     * @param pos 位置
     */
    void interact(@NotNull Entity entity,@NotNull  Entity damage,@NotNull  World world,@NotNull  BlockPos pos);
}
