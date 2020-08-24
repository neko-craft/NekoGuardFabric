package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecartEntity.class)
public abstract class MixinAbstractMinecartEntity_VehicleDamage {
    @Inject(method = "damage", at = @At(value = "RETURN"))
    private void onmVehicleDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        if(cir.getReturnValue()){
            PushHandler.getInstance().onVehicleDamage();
        }
    }
}
