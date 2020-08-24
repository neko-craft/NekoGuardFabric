package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.EntityChangeBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FallingBlockEntity.class)
public abstract class MixinFallingBlockEntity_EntityChangeBlock  extends Entity {

    public MixinFallingBlockEntity_EntityChangeBlock(EntityType<?> type, World world) {
        super(type, world);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"))
    private boolean onEntityChangeBlock(World world, BlockPos pos, boolean move){
        EntityChangeBlockCallback.EVENT.invoker().interact(this, world.getBlockState(pos).getBlock());
        return world.removeBlock(pos, move);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private boolean onEntityChangeBlock(World world, BlockPos pos, BlockState state, int flags){
        EntityChangeBlockCallback.EVENT.invoker().interact(this, world.getBlockState(pos).getBlock());
        return world.setBlockState(pos, state, flags);
    }
}
