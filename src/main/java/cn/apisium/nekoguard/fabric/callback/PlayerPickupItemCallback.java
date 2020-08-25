package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface PlayerPickupItemCallback {
    Event<PlayerPickupItemCallback> EVENT = EventFactory.createArrayBacked(PlayerPickupItemCallback.class,
            (listeners) -> (playerEntity, item, world, pos) -> {
                for (PlayerPickupItemCallback event : listeners) {
                    event.interact(playerEntity, item, world, pos);
                }
            });

    /**
     * 玩家拾取物品事件
     * @param playerEntity 玩家
     * @param item 物品
     * @param world 世界
     * @param pos 位置
     */
    void interact(@NotNull PlayerEntity playerEntity,@NotNull  Item item,@NotNull  World world,@NotNull  BlockPos pos);
}
