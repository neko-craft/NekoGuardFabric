package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface EntityBlockFormCallback {
    Event<EntityBlockFormCallback> EVENT = EventFactory.createArrayBacked(EntityBlockFormCallback.class,
            (listeners) -> (entity, block, newState, World, pos) -> {
                for (EntityBlockFormCallback event : listeners) {
                    event.interact(entity, block, newState, World, pos);
                }
            });

    /**
     * 方块搭建实体事件.
     * @param entity 实体
     * @param block 方块
     * @param newState 新的方块状态
     * @param world 世界
     * @param pos 位置
     */

    void interact(@NotNull Entity entity,@NotNull Block block,@NotNull BlockState newState,@NotNull World world,@NotNull BlockPos pos);
}
