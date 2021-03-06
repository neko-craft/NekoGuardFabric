package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public interface EntityChangeBlockCallback {
    Event<EntityChangeBlockCallback> EVENT = EventFactory.createArrayBacked(EntityChangeBlockCallback.class,
            (listeners) -> (entity, state, pos) -> {
                for (EntityChangeBlockCallback event : listeners) {
                    event.interact(entity, state, pos);
                }
            });

    /**
     * 实体更改方块基事件
     *
     * @param entity        实体
     * @param newBlockState new block state
     * @param pos           位置
     *
     * world 可以通过 entity.world 获得
     * 旧的 BlockState 以及 BlockEntity 可以通过 pos 直接从 world 中获取
     */
    void interact(@NotNull Entity entity, @NotNull BlockState newBlockState, @NotNull BlockPos pos);
}
