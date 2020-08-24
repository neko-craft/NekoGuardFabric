package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = {"net.minecraft.entity.passive.TurtleEntity$LayEggGoal"})
public abstract class MixinTurtleEntity_LayEggGoal_EntityChangeBlock {

    @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"))
    private void onEntityChangeBlock(World world, PlayerEntity player, BlockPos pos, SoundEvent sound, SoundCategory category, float volume, float pitch){
        PushHandler.getInstance().onEntityChangeBlock();
        world.playSound(player, pos, sound, category, volume,  pitch);
    }
}
