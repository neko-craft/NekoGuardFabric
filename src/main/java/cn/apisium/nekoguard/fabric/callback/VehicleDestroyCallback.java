package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface VehicleDestroyCallback {
    Event<VehicleDestroyCallback> EVENT = EventFactory.createArrayBacked(VehicleDestroyCallback.class,
            (listeners) -> (entity, damage, world , pos) -> {
                for (VehicleDestroyCallback event : listeners) {
                    event.interact(entity, damage, world , pos);
                }
            });

    /**
     * 载具损害事件
     * @param entity 载具
     * @param damage 攻击者
     * @param world 世界
     * @param pos 位置
     */
    void interact(@NotNull Entity entity, @Nullable Entity damage,@NotNull  World world,@NotNull  BlockPos pos);
}
