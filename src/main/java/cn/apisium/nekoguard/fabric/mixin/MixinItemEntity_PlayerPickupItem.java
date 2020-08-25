package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.callback.PlayerPickupItemCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntity_PlayerPickupItem {

    @Redirect(method = "onPlayerCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;sendPickup(Lnet/minecraft/entity/Entity;I)V"))
    private void onPlayerPickupItem(PlayerEntity playerEntity, Entity item, int count){
        PlayerPickupItemCallback.EVENT.invoker().interact(playerEntity);
        playerEntity.sendPickup(item,count);
    }
}
