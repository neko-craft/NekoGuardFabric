package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface BlockSpreadCallback {
    Event<BlockSpreadCallback> EVENT = EventFactory.createArrayBacked(BlockSpreadCallback.class,
            (listeners) -> (world, source, target, state, flag) -> {
                for (BlockSpreadCallback event : listeners) {
                    event.interact(world, source, target, state, flag);
                }
            });

    /**
     * 当一个方块基于自然法则地蔓延时触发此事件
     * @param world 世界
     * @param source 源坐标
     * @param target 目标坐标
     * @param state 新方块状态
     * @param flag flag
     */
    void interact(@NotNull World world, @NotNull BlockPos source, @NotNull BlockPos target, @NotNull BlockState state, int flag);
}
