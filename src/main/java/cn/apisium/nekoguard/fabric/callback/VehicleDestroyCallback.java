package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import org.jetbrains.annotations.NotNull;

public interface VehicleDestroyCallback {
    Event<VehicleDestroyCallback> EVENT = EventFactory.createArrayBacked(VehicleDestroyCallback.class,
            (listeners) -> (vehicle, source) -> {
                for (VehicleDestroyCallback event : listeners) {
                    event.interact(vehicle, source);
                }
            });



    /**
     * 载具损害事件
     *  <dl>
     *      <dt>world</dt>
     *      <dd>world 可通过 {@link Entity#world} 获得</dd>
     *      <dt>pos</dt>
     *      <dd>载具坐标 pos 可通过 {@link Entity#getBlockPos()} 和 {@link Entity#getPos()} 获取</dd>
     *      <dt>attacker</dt>
     *      <dd>攻击者 attacker 可通过 {@link DamageSource#getAttacker()} 获取, 但是 attacker 可能为 null </dd>
     *  </dl>
     *
     * @param vehicle 载具
     * @param source  伤害来源
     */
    void interact(@NotNull Entity vehicle, @NotNull DamageSource source);
}
