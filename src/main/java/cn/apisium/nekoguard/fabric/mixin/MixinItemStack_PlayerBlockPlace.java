package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.BlockPlaceCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemStack.class)
public abstract class MixinItemStack_PlayerBlockPlace {

    @Redirect(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ActionResult;isAccepted()Z"))
    private boolean onPlayerBlockPlace(ActionResult actionResult, ItemUsageContext context){
        boolean ret = actionResult.isAccepted();
        if(ret){
            BlockPlaceCallback.EVENT.invoker().interact(new BlockPlaceCallback.BlockPlace(context.getPlayer(), context.getWorld().getBlockState(context.getBlockPos()).getBlock()));
        }
        return ret;
    }
}
