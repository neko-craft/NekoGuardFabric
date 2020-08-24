package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.projectile.ProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TntBlock.class)
public abstract class MixinTntBlock_EntityChangeBlock {

    @Redirect(method = "onProjectileHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileEntity;isOnFire()Z"))
    private boolean onEntityChangeBlock(ProjectileEntity projectileEntity){
        boolean ret = projectileEntity.isOnFire();
        if(ret){
            PushHandler.getInstance().onEntityChangeBlock();
        }
        return ret;
    }
}
