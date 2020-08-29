package cn.apisium.nekoguard.fabric.mixin.vehicle.damage;


import cn.apisium.nekoguard.fabric.callback.VehicleDamageCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public abstract class MixinBoatEntity extends Entity {

    public MixinBoatEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    // EntityBoat 132
    @Inject(method = "damage",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/entity/vehicle/BoatEntity;setDamageWobbleSide(I)V",
                    ordinal = 0))
    private void onVehicleDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        VehicleDamageCallback.EVENT.invoker().interact(this, source, amount);
    }
}
