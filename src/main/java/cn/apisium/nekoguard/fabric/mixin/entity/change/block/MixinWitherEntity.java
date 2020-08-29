package cn.apisium.nekoguard.fabric.mixin.entity.change.block;

import cn.apisium.nekoguard.fabric.callback.EntityChangeBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WitherEntity.class)
public abstract class MixinWitherEntity extends HostileEntity implements SkinOverlayOwner, RangedAttackMob {

    public MixinWitherEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "mobTick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/World;breakBlock(Lnet/minecraft/util/math/BlockPos;ZLnet/minecraft/entity/Entity;)Z",
                    ordinal = 0))
    private boolean onBreakBlock(World world, BlockPos pos, boolean drop, Entity breakingEntity) {
        // 没考虑到 breakBlock 调用失败的情况
        EntityChangeBlockCallback.EVENT.invoker().interact(breakingEntity, Blocks.AIR.getDefaultState(), pos);
        return world.breakBlock(pos, drop, breakingEntity);
    }
}
