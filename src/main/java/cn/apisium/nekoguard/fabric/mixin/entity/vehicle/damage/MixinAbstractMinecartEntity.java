package cn.apisium.nekoguard.fabric.mixin.entity.vehicle.damage;

import cn.apisium.nekoguard.fabric.callback.VehicleDamageCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecartEntity.class)
public abstract class MixinAbstractMinecartEntity extends Entity {
    public MixinAbstractMinecartEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/entity/vehicle/AbstractMinecartEntity;setDamageWobbleSide(I)V",
                    ordinal = 0))
    private void onmDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        VehicleDamageCallback.EVENT.invoker().interact(this, source, amount);
    }
}
