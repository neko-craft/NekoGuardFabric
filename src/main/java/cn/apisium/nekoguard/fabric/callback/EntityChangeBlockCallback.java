package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface EntityChangeBlockCallback {
    Event<EntityChangeBlockCallback> EVENT = EventFactory.createArrayBacked(EntityChangeBlockCallback.class,
            (listeners) -> (entity, block, state, world, pos) -> {
                for (EntityChangeBlockCallback event : listeners) {
                    event.interact(entity, block, state, world, pos);
                }
            });

    /**
     * 实体更改方块基事件
     * @param entity 实体
     * @param block 老方块
     * @param state 新方块
     * @param world 世界
     * @param pos 位置
     */
    void interact(@NotNull Entity entity, @NotNull Block block , @NotNull BlockState state, @NotNull World world, @NotNull BlockPos pos);
}
