package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface BlockFormCallback {
    Event<BlockFormCallback> EVENT = EventFactory.createArrayBacked(BlockFormCallback.class,
            (listeners) -> (block, world, pos) -> {
                for (BlockFormCallback event : listeners) {
                    event.interact(block, world, pos);
                }
            });

    /**
     * 方块自然变化被放置、更改或者蔓延事件
     * @param block 方块
     * @param world 世界
     * @param pos 位置
     */
    void interact(@NotNull Block block, @NotNull World world, @NotNull BlockPos pos);
}
