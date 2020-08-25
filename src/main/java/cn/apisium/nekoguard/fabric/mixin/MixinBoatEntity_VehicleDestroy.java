package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.VehicleDestroyCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BoatEntity.class)
public abstract class MixinBoatEntity_VehicleDestroy extends Entity {

    public MixinBoatEntity_VehicleDestroy(EntityType<?> type, World world) {
        super(type, world);
    }

    @Redirect(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/BoatEntity;dropItem(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/entity/ItemEntity;"))
    private ItemEntity onVehicleDestroy(BoatEntity boatEntity, ItemConvertible item){
        VehicleDestroyCallback.EVENT.invoker().interact(this);
        return boatEntity.dropItem(item);
    }

    @Redirect(method = "fall", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/BoatEntity;remove()V"))
    private void onVehicleDestroy(BoatEntity boatEntity){
        VehicleDestroyCallback.EVENT.invoker().interact(this);
        boatEntity.remove();
    }
}
