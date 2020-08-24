package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.TripwireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;
import java.util.List;

@Mixin(TripwireBlock.class)
public abstract class MixinTripwireBlock_PlayerInteract {
    @Shadow @Final public static BooleanProperty POWERED;

    @Shadow protected abstract void update(World world, BlockPos pos, BlockState state);

    @Shadow @Final public static BooleanProperty ATTACHED;

    /**
     * @author a
     * @reason a
     */
    @Overwrite
    private void updatePowered(World world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        boolean bl = (Boolean)blockState.get(POWERED);
        boolean bl2 = false;
        List<? extends Entity> list = world.getOtherEntities((Entity)null, blockState.getOutlineShape(world, pos).getBoundingBox().offset(pos));
        if (!list.isEmpty()) {
            Iterator var7 = list.iterator();

            while(var7.hasNext()) {
                Entity entity = (Entity)var7.next();
                if (!entity.canAvoidTraps()) {
                    bl2 = true;
                    break;
                }
            }
        }

        if(bl != bl2 && bl2 && blockState.get(ATTACHED)){
            for(Object obj : list){
                if(obj != null){
                    if(obj instanceof PlayerEntity){
                        PushHandler.getInstance().onPlayerInteract();
                    } else if(obj instanceof Entity){
                        PushHandler.getInstance().onEntityInteract();
                    }
                }
            }
        }

        if (bl2 != bl) {
            blockState = (BlockState)blockState.with(POWERED, bl2);
            world.setBlockState(pos, blockState, 3);
            this.update(world, pos, blockState);
        }

        if (bl2) {
            world.getBlockTickScheduler().schedule(new BlockPos(pos), (TripwireBlock)(Object)this, 10);
        }

    }
}
