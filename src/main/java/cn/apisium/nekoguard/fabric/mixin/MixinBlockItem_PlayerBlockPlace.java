package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.BlockPlaceCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockItem.class)
public abstract class MixinBlockItem_PlayerBlockPlace {

    @Redirect(method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onPlaced(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V"))
    private void onPlayerBlockPlace(Block block, World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, ItemPlacementContext context){
        block.onPlaced(world, pos, state, placer, itemStack);
        if(placer instanceof PlayerEntity){
            BlockPlaceCallback.EVENT.invoker().interact((PlayerEntity)placer, state, world, pos);
        }
    }
}
