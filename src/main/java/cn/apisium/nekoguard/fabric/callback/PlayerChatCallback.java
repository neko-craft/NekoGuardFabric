package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

public interface PlayerChatCallback {
    Event<PlayerChatCallback> EVENT = EventFactory.createArrayBacked(PlayerChatCallback.class,
            (listeners) -> (playerEntity, message) -> {
                for (PlayerChatCallback event : listeners) {
                    event.interact(playerEntity, message);
                }
            });

    /**
     * 玩家聊天事件
     * @param playerEntity 玩家
     * @param message 消息
     */
    void interact(@NotNull PlayerEntity playerEntity,@NotNull  String message );
}
