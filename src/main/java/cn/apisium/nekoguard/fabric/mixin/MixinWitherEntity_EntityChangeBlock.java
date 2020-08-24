package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.EntityChangeBlockCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WitherEntity.class)
public abstract class MixinWitherEntity_EntityChangeBlock extends Entity {

    public MixinWitherEntity_EntityChangeBlock(EntityType<?> type, World world) {
        super(type, world);
    }

    @Redirect(method = "mobTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;syncWorldEvent(Lnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/util/math/BlockPos;I)V"))
    private void onEntityChangeBlock(World world, PlayerEntity player, int eventId, BlockPos pos, int data){
        world.syncWorldEvent(player, eventId, pos, data);
        EntityChangeBlockCallback.EVENT.invoker().interact(player, world.getBlockState(pos).getBlock());
    }
}
