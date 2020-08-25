package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.EntityChangeBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TntBlock.class)
public abstract class MixinTntBlock_EntityChangeBlock {

    @Redirect(method = "onProjectileHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileEntity;isOnFire()Z"))
    private boolean onEntityChangeBlock(ProjectileEntity projectileEntity, World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile){
        boolean ret = projectileEntity.isOnFire();
        if(ret){
            EntityChangeBlockCallback.EVENT.invoker().interact(projectileEntity, state.getBlock());
        }
        return ret;
    }
}
