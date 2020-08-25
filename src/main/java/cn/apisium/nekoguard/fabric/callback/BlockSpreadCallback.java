package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface BlockSpreadCallback {
    Event<BlockSpreadCallback> EVENT = EventFactory.createArrayBacked(BlockSpreadCallback.class,
            (listeners) -> (block, source, world, pos) -> {
                for (BlockSpreadCallback event : listeners) {
                    event.interact(block, source, world, pos);
                }
            });

    /**
     * 当一个方块基于自然法则地蔓延时触发此事件
     * @param block 方块
     * @param source 源方块
     * @param world 世界
     * @param pos 位置
     */
    void interact(@NotNull Block block, @NotNull Block source, @NotNull World world, @NotNull BlockPos pos);
}
