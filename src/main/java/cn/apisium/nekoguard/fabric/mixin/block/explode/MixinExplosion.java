package cn.apisium.nekoguard.fabric.mixin.block.explode;

import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Explosion.class)
public abstract class MixinExplosion {
    // BlockExplodeEvent
    // 处于方便与 EntityExplodeEvent 一同在 mixin.entity.explode.MixinExplosion 实现
}
