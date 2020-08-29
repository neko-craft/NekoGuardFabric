package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface BlockBurnCallback {
    Event<BlockBurnCallback> EVENT = EventFactory.createArrayBacked(BlockBurnCallback.class,
            (listeners) -> (world, source, target) -> {
                for (BlockBurnCallback event : listeners) {
                    event.interact(world, source, target);
                }
            });

    /**
     * 火烧方块事件
     *
     * @param world  世界
     * @param source 源坐标
     * @param target 目标坐标
     */
    void interact(@NotNull World world, @NotNull BlockPos source, @NotNull BlockPos target);
}
