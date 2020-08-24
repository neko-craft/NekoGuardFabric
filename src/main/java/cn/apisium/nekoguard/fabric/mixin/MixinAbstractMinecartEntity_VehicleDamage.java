package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
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
public abstract class MixinAbstractMinecartEntity_VehicleDamage  extends Entity {
    public MixinAbstractMinecartEntity_VehicleDamage(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage", at = @At(value = "RETURN"))
    private void onmVehicleDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        if(cir.getReturnValue()){
            VehicleDamageCallback.EVENT.invoker().interact(this);
        }
    }
}
