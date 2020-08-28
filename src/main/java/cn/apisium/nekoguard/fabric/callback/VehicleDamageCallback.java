package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface VehicleDamageCallback {
    Event<VehicleDamageCallback> EVENT = EventFactory.createArrayBacked(VehicleDamageCallback.class,
            (listeners) -> (vehicle, source, damage) -> {
                for (VehicleDamageCallback event : listeners) {
                    event.interact(vehicle, source, damage);
                }
            });
    /**
     * 载具受伤事件
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
     * @param damage  伤害量
     */
    void interact(@NotNull Entity vehicle, @NotNull DamageSource source, double damage);
}
