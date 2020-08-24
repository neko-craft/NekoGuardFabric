package cn.apisium.nekoguard.fabric.mixin;


import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.VehicleDamageCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BoatEntity.class)
public abstract class MixinBoatEntity_VehicleDamage extends Entity {

    public MixinBoatEntity_VehicleDamage(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract int getDamageWobbleSide();

    @Redirect(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/BoatEntity;getDamageWobbleSide()I"))
    private int onVehicleDamage(BoatEntity boatEntity){
        VehicleDamageCallback.EVENT.invoker().interact(this);
        return getDamageWobbleSide();
    }
}
