package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

public interface PlayerQuitCallback {
    Event<PlayerQuitCallback> EVENT = EventFactory.createArrayBacked(PlayerQuitCallback.class,
            (listeners) -> (playerEntity) -> {
                for (PlayerQuitCallback event : listeners) {
                    event.interact(playerEntity);
                }
            });

    /**
     * 玩家退出事件
     * @param playerEntity 玩家
     */
    void interact(@NotNull PlayerEntity playerEntity);
}
