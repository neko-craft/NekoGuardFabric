package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.EntityChangeBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LilyPadBlock.class)
public abstract class MixinLilyPadBlock_EntityChangeBlock {

    @Redirect(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;breakBlock(Lnet/minecraft/util/math/BlockPos;ZLnet/minecraft/entity/Entity;)Z"))
    private boolean onEntityChangeBlock(World world, BlockPos pos, boolean drop, Entity breakingEntity, BlockState state, World world1, BlockPos pos1, Entity entity){
        EntityChangeBlockCallback.EVENT.invoker().interact(entity, state.getBlock());
        return world.breakBlock(pos, drop, breakingEntity);
    }
}
