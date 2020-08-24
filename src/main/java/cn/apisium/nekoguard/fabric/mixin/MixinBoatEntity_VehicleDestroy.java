package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BoatEntity.class)
public abstract class MixinBoatEntity_VehicleDestroy  {

    @Redirect(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/BoatEntity;dropItem(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/entity/ItemEntity;"))
    private ItemEntity onVehicleDestroy(BoatEntity boatEntity, ItemConvertible item){
        PushHandler.getInstance().onVehicleDestroy();
        return boatEntity.dropItem(item);
    }

    @Redirect(method = "fall", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/BoatEntity;remove()V"))
    private void onVehicleDestroy(BoatEntity boatEntity){
        PushHandler.getInstance().onVehicleDestroy();
        boatEntity.remove();
    }
}
