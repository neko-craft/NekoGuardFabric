package cn.apisium.nekoguard.fabric.mixin;


import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BoatEntity.class)
public abstract class MixinBoatEntity_VehicleDamage {

    @Shadow public abstract int getDamageWobbleSide();

    @Redirect(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/BoatEntity;getDamageWobbleSide()I"))
    private int onVehicleDamage(BoatEntity boatEntity){
        PushHandler.getInstance().onVehicleDamage();
        return getDamageWobbleSide();
    }
}
