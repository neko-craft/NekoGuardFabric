package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.EntityChangeBlockCallback;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = {"net.minecraft.entity.mob.SilverfishEntity$CallForHelpGoal"})
public abstract class MixinSilverfishEntity_CallForHelpGoal_EntityChangeBlock {
    @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean onEntityChangeBlock(GameRules gameRules, GameRules.Key<GameRules.BooleanRule> rule){
        EntityChangeBlockCallback.EVENT.invoker().interact(null, null);
        return gameRules.getBoolean(rule);
    }
}
