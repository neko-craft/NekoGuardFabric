package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = {"net.minecraft.entity.passive.BeeEntity$GrowCropsGoal"})
public abstract class MixinBeeEntity_GrowCropsGoal_EntityChangeBlock {

    @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;syncWorldEvent(ILnet/minecraft/util/math/BlockPos;I)V"))
    private void onEntityChangeBlock(World world, int eventId, BlockPos pos, int data){
        PushHandler.getInstance().onEntityChangeBlock();
        world.syncWorldEvent(eventId, pos, data);
    }
}
