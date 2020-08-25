package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface PlayerDropItemCallback {
    Event<PlayerDropItemCallback> EVENT = EventFactory.createArrayBacked(PlayerDropItemCallback.class,
            (listeners) -> (playerEntity, item, world, pos) -> {
                for (PlayerDropItemCallback event : listeners) {
                    event.interact(playerEntity, item, world, pos);
                }
            });

    /**
     * 玩家丢弃事件
     * @param playerEntity 玩家
     * @param item 物品
     * @param world 事件
     * @param pos 位置
     */
    void interact(PlayerEntity playerEntity, Item item, World world, BlockPos pos);
}
