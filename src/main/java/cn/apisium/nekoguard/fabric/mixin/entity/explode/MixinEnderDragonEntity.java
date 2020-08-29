package cn.apisium.nekoguard.fabric.mixin.entity.explode;

import cn.apisium.nekoguard.fabric.callback.EntityExplodeCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnderDragonEntity.class)
public abstract class MixinEnderDragonEntity extends MobEntity implements Monster {

    public MixinEnderDragonEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    private List<BlockPos> destroyedPosList;
    private List<BlockState> destroyedStateList;

    // 用于在 destroyBlocks 方法调用前初始化 destroyedPosList, destroyedPosList
    @Inject(method = "destroyBlocks",
            at = @At(value = "HEAD"))
    private void preDestroyBlocks(Box box, CallbackInfoReturnable<Boolean> ci) {
        destroyedPosList = new ArrayList<>();
        destroyedStateList = new ArrayList<>();
    }

    // EntityEnderDragon 439
    // 获取被摧毁的方块的列表
    @Redirect(method = "destroyBlocks",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z",
                    ordinal = 0))
    private boolean redirectRemoveBlock(World world, BlockPos pos, boolean move) {
        this.destroyedPosList.add(pos);
        this.destroyedStateList.add(world.getBlockState(pos));
        return world.removeBlock(pos, move);
    }

    // 生成爆炸事件
    @Inject(method = "destroyBlocks",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/WorldAccess;syncWorldEvent(ILnet/minecraft/util/math/BlockPos;I)V",
                    ordinal = 0))
    private void onDestroyBlocks(Box box, CallbackInfoReturnable<Boolean> ci) {
        EntityExplodeCallback.EVENT.invoker().interact(this, this.destroyedPosList, this.destroyedStateList);
    }
}
