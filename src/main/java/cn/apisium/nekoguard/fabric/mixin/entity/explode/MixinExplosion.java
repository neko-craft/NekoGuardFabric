package cn.apisium.nekoguard.fabric.mixin.entity.explode;

import cn.apisium.nekoguard.fabric.callback.BlockExplodeCallback;
import cn.apisium.nekoguard.fabric.callback.EntityExplodeCallback;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Mixin(Explosion.class)
public abstract class MixinExplosion {
    @Final
    @Shadow
    private double x;

    @Final
    @Shadow
    private double y;

    @Final
    @Shadow
    private double z;

    @Final
    @Shadow
    private Entity entity;

    @Final
    @Shadow
    private List<BlockPos> affectedBlocks;

    @Final
    @Shadow
    private World world;

    private List<BlockPos> destroyedPosList;
    private List<BlockState> destroyedStateList;

    // EntityExplodeEvent Explosion 249
    // BlockExplodeEvent  Explosion 255
    // 用于在 affectWorld 方法调用前初始化 destroyedPosList, destroyedPosList
    @Inject(method = "affectWorld",
            at = @At(value = "HEAD"))
    private void preAffectWorld(boolean bl, CallbackInfo ci) {
        destroyedPosList = new ArrayList<>();
        destroyedStateList = new ArrayList<>();
    }

    @Inject(method = "affectWorld",
            at = @At(value = "INVOKE_ASSIGN",
                    target = "Ljava/util/List;iterator()Ljava/util/Iterator;",
                    ordinal = 0))
    private void logDestroyedBlock(boolean bl, CallbackInfo ci) {
        this.affectedBlocks.forEach(pos -> {
            BlockState state = this.world.getBlockState(pos);
            if (!state.isAir()) {
                this.destroyedPosList.add(pos);
                this.destroyedStateList.add(state);
            }
        });
        if (this.entity != null) {
            EntityExplodeCallback.EVENT.invoker().interact(entity, this.destroyedPosList, this.destroyedStateList);
        } else {
            BlockPos pos = new BlockPos(this.x, this.y, this.z);
            BlockExplodeCallback.EVENT.invoker().interact(this.destroyedPosList, this.destroyedStateList, this.world, pos);
        }
    }
}
