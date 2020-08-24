package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.EntityInteractCallback;
import cn.apisium.nekoguard.fabric.callback.PlayerInteractCallback;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.WeightedPressurePlateBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WeightedPressurePlateBlock.class)
public abstract class MixinWeightedPressurePlateBlock_PlayerInteract  extends AbstractPressurePlateBlock {

    protected MixinWeightedPressurePlateBlock_PlayerInteract(Settings settings) {
        super(settings);
    }

    @Inject(method = "getRedstoneOutput(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)I", at = @At("HEAD"))
    private void onPlayerInteract(World world, BlockPos pos, CallbackInfoReturnable<Integer> cir){
        for (Entity entity : world.getEntitiesIncludingUngeneratedChunks(Entity.class, WeightedPressurePlateBlock.BOX.offset(pos))) {
            if (entity instanceof PlayerEntity) {
                PlayerInteractCallback.EVENT.invoker().interact((PlayerEntity) entity);
            } else {
                EntityInteractCallback.EVENT.invoker().interact(entity);
            }
        }
    }
}
