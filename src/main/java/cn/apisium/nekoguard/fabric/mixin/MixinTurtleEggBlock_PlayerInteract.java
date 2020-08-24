package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TurtleEggBlock.class)
public abstract class MixinTurtleEggBlock_PlayerInteract {
    @Shadow protected abstract boolean breaksEgg(World world, Entity entity);

    @Shadow protected abstract void breakEgg(World world, BlockPos pos, BlockState state);

    /**
     * @author a
     * @reason a
     */
    @Overwrite
    private void tryBreakEgg(World world, BlockPos blockPos, Entity entity, int inverseChance) {
        if (breaksEgg(world, entity)) {
            if (!world.isClient && world.random.nextInt(inverseChance) == 0) {
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.isOf(Blocks.TURTLE_EGG)) {
                    if(entity instanceof PlayerEntity){
                        PushHandler.getInstance().onPlayerInteract();
                    } else {
                        PushHandler.getInstance().onEntityInteract();
                    }
                    this.breakEgg(world, blockPos, blockState);
                }
            }
        }
    }
}
