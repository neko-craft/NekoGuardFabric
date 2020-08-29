package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface BlockExplodeCallback {
    Event<BlockExplodeCallback> EVENT = EventFactory.createArrayBacked(BlockExplodeCallback.class,
            (listeners) -> (posList, blockStateList, world, pos) -> {
                for (BlockExplodeCallback event : listeners) {
                    event.interact(posList, blockStateList, world, pos);
                }
            });

    /**
     * 方块爆炸事件
     *
     * @param posList        受波及的非空气方块位置列表
     * @param blockStateList 受波及的非空气方块列表
     * @param world          爆炸所在的世界
     * @param pos            爆炸位置
     * 爆炸方块可由 world.getBlockState 获得
     */
    void interact(@NotNull List<BlockPos> posList, @NotNull List<BlockState> blockStateList,
                  @NotNull World world, @NotNull BlockPos pos);
}