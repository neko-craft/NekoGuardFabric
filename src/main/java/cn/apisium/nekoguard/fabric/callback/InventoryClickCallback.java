package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface InventoryClickCallback {
    Event<InventoryClickCallback> EVENT = EventFactory.createArrayBacked(InventoryClickCallback.class,
            (listeners) -> (playerEntity,action,click,inventory,slot,world,blockPos) -> {
                for (InventoryClickCallback event : listeners) {
                    event.interact(playerEntity,action,click,inventory,slot,world,blockPos);
                }
            });


    /**
     * 玩家点击容器事件
     * @param playerEntity 玩家
     * @param action 类型
     * @param click 点击
     * @param inventory 操作的容器
     * @param slot 点击位置
     * @param world 世界
     * @param pos 位置
     */
    void interact(@NotNull PlayerEntity playerEntity,@NotNull  Action action,@NotNull  Click click,@NotNull  Inventory inventory, @NotNull int slot,@NotNull  World world,@NotNull  BlockPos pos);

    enum Action{
        NOTHING,
        PICKUP_ALL,
        PICKUP_SOME,
        PICKUP_HALF,
        PICKUP_ONE,
        PLACE_ALL,
        PLACE_SOME,
        PLACE_ONE,
        SWAP_WITH_CURSOR,
        DROP_ALL_CURSOR,
        DROP_ONE_CURSOR,
        DROP_ALL_SLOT,
        DROP_ONE_SLOT,
        MOVE_TO_OTHER_INVENTORY,
        HOTBAR_MOVE_AND_READD,
        HOTBAR_SWAP,
        CLONE_STACK,
        COLLECT_TO_CURSOR,
        UNKNOWN;
    }

    enum Click{
        LEFT,
        SHIFT_LEFT,
        RIGHT,
        SHIFT_RIGHT,
        WINDOW_BORDER_LEFT,
        WINDOW_BORDER_RIGHT,
        MIDDLE,
        NUMBER_KEY,
        DOUBLE_CLICK,
        DROP,
        CONTROL_DROP,
        CREATIVE,
        SWAP_OFFHAND,
        UNKNOWN;
    }
}
