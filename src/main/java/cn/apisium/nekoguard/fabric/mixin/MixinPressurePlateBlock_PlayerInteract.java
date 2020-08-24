package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.EntityInteractCallback;
import cn.apisium.nekoguard.fabric.callback.PlayerInteractCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;

@Mixin(PressurePlateBlock.class)
public abstract class MixinPressurePlateBlock_PlayerInteract {


    @Shadow protected abstract int getRedstoneOutput(BlockState state);

    @Redirect(method = "getRedstoneOutput(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)I", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;next()Ljava/lang/Object;"))
    private <E> E onPlayerInteract(Iterator<E> iterator, World world, BlockPos pos){
        E ret = iterator.next();
        if(getRedstoneOutput(world.getBlockState(pos)) == 0){
            if(ret instanceof PlayerEntity){
                PlayerInteractCallback.EVENT.invoker().interact((PlayerEntity) ret);
            } else {
                EntityInteractCallback.EVENT.invoker().interact((Entity) ret);
            }
        }
        return ret;
    }
}
