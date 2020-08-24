package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.BlockPlaceCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemStack.class)
public abstract class MixinItemStack_PlayerBlockPlace {

    @Redirect(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ActionResult;isAccepted()Z"))
    private boolean onPlayerBlockPlace(ActionResult actionResult, ItemUsageContext context){
        boolean ret = actionResult.isAccepted();
        if(ret){
            PlayerEntity player = context.getPlayer();
            if(player != null){
                World world = context.getWorld();
                BlockPos pos = context.getBlockPos();
                BlockState state = world.getBlockState(pos);
                BlockPlaceCallback.EVENT.invoker().interact(player,state,world, pos);
            }
        }
        return ret;
    }
}
