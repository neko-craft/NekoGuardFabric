package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.PlayerBucketFillCallback;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CowEntity.class)
public abstract class MixinCowEntity_PlayerBucketFill {

    @Redirect(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/CowEntity;isBaby()Z"))
    private boolean onPlayerBucketFill(CowEntity cowEntity, PlayerEntity player, Hand hand){
        boolean ret = cowEntity.isBaby();
        if(!ret){
            PlayerBucketFillCallback.EVENT.invoker().interact(player, null);
        }
        return ret;
    }
}
