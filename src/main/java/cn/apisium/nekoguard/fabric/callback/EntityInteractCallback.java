package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface EntityInteractCallback {
    Event<EntityInteractCallback> EVENT = EventFactory.createArrayBacked(EntityInteractCallback.class,
            (listeners) -> (entity, pos) -> {
                for (EntityInteractCallback event : listeners) {
                    event.interact(entity, pos);
                }
            });

    /**
     * 实体互交事件
     * @param entity 实体
     * @param pos 位置
     * world 可以通过 entity 获得
     */
    void interact(@NotNull Entity entity, @NotNull BlockPos pos);
}
