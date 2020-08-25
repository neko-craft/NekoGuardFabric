package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.PlayerInteractCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class MixinServerPlayNetworkHandler_PlayerInteract {

    @Shadow public ServerPlayerEntity player;

    @Redirect(method = "onPlayerInteractBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"))
    private ItemStack onPlayerInteract(ServerPlayerEntity serverPlayerEntity, Hand hand){
        ItemStack ret = serverPlayerEntity.getStackInHand(hand);
        if(!ret.isEmpty()){
            PlayerInteractCallback.EVENT.invoker().interact(player);
        }
        return ret;
    }

    @Inject(method = "onHandSwing", at = @At("HEAD"))
    private void onPlayerInteract(HandSwingC2SPacket packet, CallbackInfo ci){
        PlayerInteractCallback.EVENT.invoker().interact(player);
    }
}
