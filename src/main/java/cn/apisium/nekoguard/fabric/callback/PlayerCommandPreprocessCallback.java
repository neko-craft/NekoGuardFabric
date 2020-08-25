package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

public interface PlayerCommandPreprocessCallback {
    Event<PlayerCommandPreprocessCallback> EVENT = EventFactory.createArrayBacked(PlayerCommandPreprocessCallback.class,
            (listeners) -> (playerEntity, message) -> {
                for (PlayerCommandPreprocessCallback event : listeners) {
                    event.interact(playerEntity, message);
                }
            });

    /**
     * 玩家使用命令事件
     * @param playerEntity 玩家实体
     * @param message 命令
     */
    void interact(@NotNull PlayerEntity playerEntity, @NotNull String message );
}
