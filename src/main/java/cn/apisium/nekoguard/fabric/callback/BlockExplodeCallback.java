package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface BlockExplodeCallback {
    Event<BlockExplodeCallback> EVENT = EventFactory.createArrayBacked(BlockExplodeCallback.class,
            (listeners) -> (block, posList, blocks, world, pos) -> {
                for (BlockExplodeCallback event : listeners) {
                    event.interact(block, posList, blocks, world, pos);
                }
            });
    /**
     * 方块爆炸事件
     * @param block 爆炸方块
     * @param posList 受波及的非空气方块位置列表
     * @param blocks 受波及的非空气方块列表
     * @param world 爆炸所在的世界
     * @param pos 爆炸位置
     */
    void interact(@NotNull Block block, @NotNull List<BlockPos> posList, @NotNull List<Block> blocks, @NotNull World world, @NotNull BlockPos pos);
}