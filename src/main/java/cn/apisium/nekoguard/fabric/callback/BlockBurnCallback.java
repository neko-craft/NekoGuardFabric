package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface BlockBurnCallback {
    Event<BlockBurnCallback> EVENT = EventFactory.createArrayBacked(BlockBurnCallback.class,
            (listeners) -> (block, world, pos) -> {
                for (BlockBurnCallback event : listeners) {
                    event.interact(block, world, pos);
                }
            });

    /**
     * 火烧方块事件
     * @param block 被烧的方块
     * @param world 所在世界
     * @param pos 所在位置
     */
    void interact(@NotNull Block block, @NotNull World world, @NotNull BlockPos pos);
}
