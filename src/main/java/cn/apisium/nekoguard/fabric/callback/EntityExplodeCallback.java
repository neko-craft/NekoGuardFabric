package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface EntityExplodeCallback {
    Event<EntityExplodeCallback> EVENT = EventFactory.createArrayBacked(EntityExplodeCallback.class,
            (listeners) -> (entity, posList, stateList) -> {
                for (EntityExplodeCallback event : listeners) {
                    event.interact(entity, posList, stateList);
                }
            });

    /**
     * 实体爆炸事件
     *
     * @param entity    爆炸实体
     * @param posList   受波及的非空气方块位置列表
     * @param stateList 受波及的非空气方块列表
     * 爆炸所在的世界可以通过 entity.world 获得
     * 爆炸位置可以通过 entity.getBlockPos 获得
     */
    void interact(@NotNull Entity entity, @NotNull List<BlockPos> posList, @NotNull List<BlockState> stateList);
}
