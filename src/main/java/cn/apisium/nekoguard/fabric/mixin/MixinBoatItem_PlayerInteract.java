package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BoatItem.class)
public abstract class MixinBoatItem_PlayerInteract {

    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/BoatEntity;setBoatType(Lnet/minecraft/entity/vehicle/BoatEntity$Type;)V"))
    private void onPlayerInteract(BoatEntity boatEntity, BoatEntity.Type type){
        PushHandler.getInstance().onPlayerInteract();
        boatEntity.setBoatType(type);
    }
}
