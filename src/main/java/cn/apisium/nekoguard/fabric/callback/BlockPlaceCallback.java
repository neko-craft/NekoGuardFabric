package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface BlockPlaceCallback {
    Event<BlockPlaceCallback> EVENT = EventFactory.createArrayBacked(BlockPlaceCallback.class,
            (listeners) -> (player, state,world, pos) -> {
                for (BlockPlaceCallback event : listeners) {
                    event.interact(player, state,world, pos);
                }
    });

    /**
     * 玩家放置方块事件
     * @param player 玩家
     * @param state 方块
     * @param world 世界
     * @param pos 位置
     */
    void interact(@NotNull PlayerEntity player,@NotNull BlockState state,@NotNull World world,@NotNull BlockPos pos);
}
