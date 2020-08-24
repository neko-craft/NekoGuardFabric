package cn.apisium.nekoguard.fabric.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.block.Block;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RedstoneOreBlock.class)
public abstract class MixinRedstoneOreBlock_PlayerInteractAndEntityInteract extends Block {

    @Shadow
    protected static void spawnParticles(World world, BlockPos pos) {
    }

    public MixinRedstoneOreBlock_PlayerInteractAndEntityInteract(Settings settings) {
        super(settings);
    }

    @Redirect(method = "onSteppedOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onSteppedOn(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;)V"))
    private void onPlayerInteractAndEntityInteract(Block block, World world, BlockPos pos, Entity entity){
        if(entity instanceof PlayerEntity){
            PushHandler.getInstance().onPlayerInteract();
        } else {
            PushHandler.getInstance().onEntityInteract();
        }
        super.onSteppedOn(world, pos, entity);
    }

    @Environment(EnvType.CLIENT)
    @Redirect(method = "randomDisplayTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/RedstoneOreBlock;spawnParticles(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V"))
    private void onPlayerInteract(World world, BlockPos pos){
        PushHandler.getInstance().onPlayerInteract();
        spawnParticles(world, pos);
    }
}
