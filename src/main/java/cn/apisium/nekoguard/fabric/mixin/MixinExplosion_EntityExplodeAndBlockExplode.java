package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.callback.BlockExplodeCallback;
import cn.apisium.nekoguard.fabric.callback.EntityExplodeCallback;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import cn.apisium.nekoguard.fabric.PushHandler;
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

import java.util.*;

@Mixin(Explosion.class)
public abstract class MixinExplosion_EntityExplodeAndBlockExplode {

    @Shadow @Final private World world;

    @Shadow @Final private double x;

    @Shadow @Final private double y;

    @Shadow @Final private double z;

    @Shadow @Final private List<BlockPos> affectedBlocks;

    @Shadow @Final private Explosion.DestructionType destructionType;

    @Shadow @Final private boolean createFire;

    @Shadow @Final private Random random;

    @Shadow @Final private float power;

    @Shadow @Final private Entity entity;

    @Shadow
    protected static void method_24023(ObjectArrayList<Pair<ItemStack, BlockPos>> objectArrayList, ItemStack itemStack, BlockPos blockPos) {
    }

    /**
     * @author a
     * @reason a
     */
    @Overwrite
    public void affectWorld(boolean bl) {
        if (this.world.isClient) {
            this.world.playSound(this.x, this.y, this.z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F, false);
        }

        boolean bl2 = this.destructionType != Explosion.DestructionType.NONE;
        if (bl) {
            if (this.power >= 2.0F && bl2) {
                this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
            } else {
                this.world.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
            }
        }

        ArrayList<Block> blockList = new ArrayList<>();
        ArrayList<BlockPos> blockPosList = new ArrayList<>();

        affectedBlocks.forEach(ab->{
            BlockState bs = world.getBlockState(ab);
            if(!bs.isAir()){
                blockList.add(bs.getBlock());
                blockPosList.add(ab);
            }
        });
        if(entity != null){
            EntityExplodeCallback.EVENT.invoker().interact(entity, blockPosList, blockList, world, entity.getBlockPos());
        } else {
            BlockExplodeCallback.EVENT.invoker().interact(world.getBlockState(new BlockPos(x,y,z)).getBlock(), blockPosList, blockList, world, new BlockPos(x,y,z));
        }

        if (bl2) {
            ObjectArrayList<Pair<ItemStack, BlockPos>> objectArrayList = new ObjectArrayList();
            Collections.shuffle(this.affectedBlocks, this.world.random);
            Iterator var4 = this.affectedBlocks.iterator();

            while(var4.hasNext()) {
                BlockPos blockPos = (BlockPos)var4.next();
                BlockState blockState = this.world.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if (!blockState.isAir()) {
                    BlockPos blockPos2 = blockPos.toImmutable();
                    this.world.getProfiler().push("explosion_blocks");
                    if (block.shouldDropItemsOnExplosion((Explosion) (Object)this) && this.world instanceof ServerWorld) {
                        BlockEntity blockEntity = block.hasBlockEntity() ? this.world.getBlockEntity(blockPos) : null;
                        LootContext.Builder builder = (new LootContext.Builder((ServerWorld)this.world)).random(this.world.random).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(blockPos)).parameter(LootContextParameters.TOOL, ItemStack.EMPTY).optionalParameter(LootContextParameters.BLOCK_ENTITY, blockEntity).optionalParameter(LootContextParameters.THIS_ENTITY, this.entity);
                        if (this.destructionType == Explosion.DestructionType.DESTROY) {
                            builder.parameter(LootContextParameters.EXPLOSION_RADIUS, this.power);
                        }

                        blockState.getDroppedStacks(builder).forEach((itemStack) -> {
                            method_24023(objectArrayList, itemStack, blockPos2);
                        });
                    }

                    this.world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                    block.onDestroyedByExplosion(this.world, blockPos, (Explosion) (Object)this);
                    this.world.getProfiler().pop();
                }
            }

            ObjectListIterator var12 = objectArrayList.iterator();

            while(var12.hasNext()) {
                Pair<ItemStack, BlockPos> pair = (Pair)var12.next();
                Block.dropStack(this.world, (BlockPos)pair.getSecond(), (ItemStack)pair.getFirst());
            }
        }

        if (this.createFire) {
            Iterator var11 = this.affectedBlocks.iterator();

            while(var11.hasNext()) {
                BlockPos blockPos3 = (BlockPos)var11.next();
                if (this.random.nextInt(3) == 0 && this.world.getBlockState(blockPos3).isAir() && this.world.getBlockState(blockPos3.down()).isOpaqueFullCube(this.world, blockPos3.down())) {
                    this.world.setBlockState(blockPos3, AbstractFireBlock.getState(this.world, blockPos3));
                }
            }
        }

    }
}
