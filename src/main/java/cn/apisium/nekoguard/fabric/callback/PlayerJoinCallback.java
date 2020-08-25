package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

public interface PlayerJoinCallback {
    Event<PlayerJoinCallback> EVENT = EventFactory.createArrayBacked(PlayerJoinCallback.class,
            (listeners) -> (playerEntity) -> {
                for (PlayerJoinCallback event : listeners) {
                    event.interact(playerEntity);
                }
            });

    /**
     * 玩家加入事件
     * @param playerEntity 玩家
     */
    void interact(@NotNull PlayerEntity playerEntity);
}
