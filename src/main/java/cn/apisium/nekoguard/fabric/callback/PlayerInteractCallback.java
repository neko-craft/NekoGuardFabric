package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PlayerInteractCallback {
    Event<PlayerInteractCallback> EVENT = EventFactory.createArrayBacked(PlayerInteractCallback.class,
            (listeners) -> (playerEntity, action, click, slot, stack, world, pos) -> {
                for (PlayerInteractCallback event : listeners) {
                    event.interact(playerEntity, action, click, slot, stack, world, pos);
                }
            });

    /**
     * 玩家交互事件
     * @param playerEntity 玩家
     * @param action 类型
     * @param click 交互方块
     * @param slot 用什么交互
     * @param stack 物品
     * @param world 世界
     * @param pos 位置
     */
    void interact(@NotNull PlayerEntity playerEntity, @NotNull Action action, @Nullable Block click,@NotNull EquipmentSlot slot,@Nullable ItemStack stack,@NotNull World world,@NotNull BlockPos pos);

    enum Action{
        LEFT_CLICK_AIR,
        LEFT_CLICK_BLOCK,
        PHYSICAL,
        RIGHT_CLICK_AIR,
        RIGHT_CLICK_BLOCK;
    }
}
