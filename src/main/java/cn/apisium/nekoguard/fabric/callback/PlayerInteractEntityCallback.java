package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface PlayerInteractEntityCallback {
    Event<PlayerInteractEntityCallback> EVENT = EventFactory.createArrayBacked(PlayerInteractEntityCallback.class,
            (listeners) -> (playerEntity, slot, entity, world, pos) -> {
                for (PlayerInteractEntityCallback event : listeners) {
                    event.interact(playerEntity, slot, entity, world, pos);
                }
            });

    /**
     * 玩家交互实体事件
     * @param playerEntity 玩家
     * @param slot 手
     * @param entity 实体
     * @param world 世界
     * @param pos 位置
     */
    void interact(PlayerEntity playerEntity, EquipmentSlot slot, Entity entity, World world, BlockPos pos);
}
