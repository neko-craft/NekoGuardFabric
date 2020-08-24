package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.entity.passive.CowEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CowEntity.class)
public abstract class MixinCowEntity_PlayerBucketFill {

    @Redirect(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/CowEntity;isBaby()Z"))
    private boolean onPlayerBucketFill(CowEntity cowEntity){
        boolean ret = cowEntity.isBaby();
        if(!ret){
            System.out.println("==========");
            System.out.println("事件： PlayerBucketFill");
            PushHandler.getInstance().onPlayerBucketFill();
        }
        return ret;
    }
}
