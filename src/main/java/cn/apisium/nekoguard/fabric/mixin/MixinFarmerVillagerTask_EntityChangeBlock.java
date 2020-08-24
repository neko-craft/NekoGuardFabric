package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.task.FarmerVillagerTask;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FarmerVillagerTask.class)
public abstract class MixinFarmerVillagerTask_EntityChangeBlock {

    @Redirect(method = "keepRunning", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;breakBlock(Lnet/minecraft/util/math/BlockPos;ZLnet/minecraft/entity/Entity;)Z"))
    private boolean onEntityChangeBlock(ServerWorld serverWorld, BlockPos pos, boolean drop, Entity breakingEntity){
        PushHandler.getInstance().onEntityChangeBlock();
        return serverWorld.breakBlock(pos, drop, breakingEntity);
    }

    @Redirect(method = "keepRunning", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"))
    private void onEntityChangeBlock(ServerWorld serverWorld, PlayerEntity player, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch){
        PushHandler.getInstance().onEntityChangeBlock();
        serverWorld.playSound(player, x,y,z, sound, category, volume, pitch);
    }
}
