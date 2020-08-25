package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.EntityChangeBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RedstoneOreBlock.class)
public abstract class MixinRedstoneOreBlock_EntityChangeBlock extends Block {

    public MixinRedstoneOreBlock_EntityChangeBlock(Settings settings) {
        super(settings);
    }

    @Shadow
    protected static void light(BlockState state, World world, BlockPos pos) {
    }

    @Inject(method = "onBlockBreakStart", at = @At("HEAD"))
    private void onEntityChangeBlock(BlockState state, World world, BlockPos pos, PlayerEntity player, CallbackInfo ci){
        EntityChangeBlockCallback.EVENT.invoker().interact(player, this);
    }

    @Inject(method = "onSteppedOn", at = @At("HEAD"))
    private void onEntityChangeBlock(World world, BlockPos pos, Entity entity, CallbackInfo ci){
        EntityChangeBlockCallback.EVENT.invoker().interact(entity, this);
    }

    @Redirect(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/RedstoneOreBlock;light(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V"))
    private void onEntityChangeBlock(BlockState state, World world,  BlockPos pos){
        EntityChangeBlockCallback.EVENT.invoker().interact(null, this);
        light(state, world, pos);
    }
}
