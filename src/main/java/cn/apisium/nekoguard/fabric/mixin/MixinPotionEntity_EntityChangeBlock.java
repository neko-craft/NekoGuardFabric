package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PotionEntity.class)
public abstract class MixinPotionEntity_EntityChangeBlock {

    @Redirect(method = "extinguishFire", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/tag/Tag;)Z"))
    private boolean onEntityChangeBlock(BlockState blockState, Tag<Block> tag){
        boolean ret = blockState.isIn(tag);
        PushHandler.getInstance().onEntityChangeBlock();
        return  ret;
    }

    @Redirect(method = "extinguishFire", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;syncWorldEvent(Lnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/util/math/BlockPos;I)V"))
    private void onEntityChangeBlock(World world, PlayerEntity player, int eventId, BlockPos pos, int data){
        PushHandler.getInstance().onEntityChangeBlock();
        world.syncWorldEvent(player, eventId, pos, data);
    }
}
