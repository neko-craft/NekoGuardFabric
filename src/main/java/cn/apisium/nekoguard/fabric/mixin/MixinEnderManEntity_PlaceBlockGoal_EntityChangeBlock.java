package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.mob.EndermanEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = {"net.minecraft.entity.mob.EndermanEntity$PlaceBlockGoal"})
public abstract class MixinEnderManEntity_PlaceBlockGoal_EntityChangeBlock {
    @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/EndermanEntity;setCarriedBlock(Lnet/minecraft/block/BlockState;)V"))
    private void onEntityChangeBlock(EndermanEntity endermanEntity, BlockState state){
        PushHandler.getInstance().onEntityChangeBlock();
        endermanEntity.setCarriedBlock(state);
    }
}
