package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.EntityChangeBlockCallback;
import net.minecraft.block.CropBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CropBlock.class)
public abstract class MixinCropBlock_EntityChangeBlock  extends PlantBlock implements Fertilizable {

    protected MixinCropBlock_EntityChangeBlock(Settings settings) {
        super(settings);
    }

    @Redirect(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;breakBlock(Lnet/minecraft/util/math/BlockPos;ZLnet/minecraft/entity/Entity;)Z"))
    private boolean EntityChangeBlock(World world, BlockPos pos, boolean drop, Entity breakingEntity){
        EntityChangeBlockCallback.EVENT.invoker().interact(breakingEntity, world.getBlockState(pos).getBlock());
        return world.breakBlock(pos, drop, breakingEntity);
    }
}
