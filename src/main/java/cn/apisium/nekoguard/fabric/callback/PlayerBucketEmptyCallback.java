package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface PlayerBucketEmptyCallback {
    Event<PlayerBucketEmptyCallback> EVENT = EventFactory.createArrayBacked(PlayerBucketEmptyCallback.class,
            (listeners) -> (playerEntity, block, click, bucket, world, pos) -> {
                for (PlayerBucketEmptyCallback event : listeners) {
                    event.interact(playerEntity, block, click, bucket, world, pos);
                }
            });

    /**
     * 用完一只桶事件
     * @param playerEntity 玩家
     * @param block 流体方块
     * @param click 被点击的方块
     * @param bucket 桶
     * @param world 世界
     * @param pos 位置
     */
    void interact(@NotNull PlayerEntity playerEntity, @NotNull Block block , @NotNull Block click,@NotNull  ItemStack bucket,@NotNull  World world,@NotNull  BlockPos pos);
}
