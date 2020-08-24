package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallMountedBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(AbstractButtonBlock.class)
public abstract class MixinAbstractButtonBlock_EntityInteract extends WallMountedBlock {

    @Shadow @Final public static BooleanProperty POWERED;

    protected MixinAbstractButtonBlock_EntityInteract(Settings settings) {
        super(settings);
    }

    @Shadow protected abstract void updateNeighbors(BlockState state, World world, BlockPos pos);

    @Shadow protected abstract void playClickSound(PlayerEntity player, WorldAccess world, BlockPos pos, boolean powered);

    @Shadow protected abstract int getPressTicks();

    /**
     * @author a
     * @reason a
     */
    @Overwrite
    private void tryPowerWithProjectiles(BlockState state, World world, BlockPos pos) {
        List<? extends Entity> list = world.getNonSpectatingEntities(PersistentProjectileEntity.class, state.getOutlineShape(world, pos).getBoundingBox().offset(pos));
        boolean bl = !list.isEmpty();
        boolean bl2 = state.get(POWERED);
        if(bl2 != bl && bl){
            for(Object obj : list){
                if(obj != null){
                    PushHandler.getInstance().onEntityInteract();
                }
            }
        }
        if (bl != bl2) {
            world.setBlockState(pos, (BlockState)state.with(POWERED, bl), 3);
            this.updateNeighbors(state, world, pos);
            this.playClickSound(null, world, pos, bl);
        }

        if (bl) {
            world.getBlockTickScheduler().schedule(new BlockPos(pos), this, getPressTicks());
        }

    }
}
