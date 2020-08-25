package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public interface EntityDeathCallback {
    Event<EntityDeathCallback> EVENT = EventFactory.createArrayBacked(EntityDeathCallback.class,
            (listeners) -> (entity, itemStacks, world, pos) -> {
                for (EntityDeathCallback event : listeners) {
                    event.interact(entity, itemStacks, world, pos);
                }
            });

    /**
     * 实体死亡事件
     * @param entity 实体
     * @param itemStacks 掉落方块
     * @param world 世界
     * @param pos 位置
     */
    void interact(@NotNull Entity entity, @NotNull List<ItemStack> itemStacks,@NotNull  World world, @NotNull BlockPos pos);
}
