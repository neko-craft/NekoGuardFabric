package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.BlockSpreadCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;
import java.util.Random;


@Mixin(VineBlock.class)
public abstract class MixinVineBlock_BlockSpread extends Block {

    public MixinVineBlock_BlockSpread(Settings settings) {
        super(settings);
    }

    @Shadow
    public static BooleanProperty getFacingProperty(Direction direction) {
        return null;
    }

    @Shadow protected abstract boolean canGrowAt(BlockView world, BlockPos pos);

    @Shadow
    public static boolean shouldConnectTo(BlockView world, BlockPos pos, Direction direction) {
        return false;
    }

    @Shadow protected abstract boolean shouldHaveSide(BlockView world, BlockPos pos, Direction side);

    @Shadow protected abstract boolean hasHorizontalSide(BlockState state);

    @Shadow protected abstract BlockState getGrownState(BlockState above, BlockState state, Random random);

    @Shadow @Final public static BooleanProperty UP;

    /**
     * @author a
     * @reason a
     */
    @Overwrite
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.random.nextInt(4) == 0) {
            Direction direction = Direction.random(random);
            BlockPos blockPos = pos.up();
            BlockPos blockPos2;
            BlockState blockState3;
            Direction direction5;
            if (direction.getAxis().isHorizontal() && !(Boolean)state.get(getFacingProperty(direction))) {
                if (this.canGrowAt(world, pos)) {
                    blockPos2 = pos.offset(direction);
                    blockState3 = world.getBlockState(blockPos2);
                    if (blockState3.isAir()) {
                        direction5 = direction.rotateYClockwise();
                        Direction direction3 = direction.rotateYCounterclockwise();
                        boolean bl = (Boolean)state.get(getFacingProperty(direction5));
                        boolean bl2 = (Boolean)state.get(getFacingProperty(direction3));
                        BlockPos blockPos3 = blockPos2.offset(direction5);
                        BlockPos blockPos4 = blockPos2.offset(direction3);
                        if (bl && shouldConnectTo(world, blockPos3, direction5)) {
                            BlockSpreadCallback.EVENT.invoker().interact(world.getBlockState(blockPos2).getBlock());
                            world.setBlockState(blockPos2, (BlockState)this.getDefaultState().with(getFacingProperty(direction5), true), 2);
                        } else if (bl2 && shouldConnectTo(world, blockPos4, direction3)) {
                            BlockSpreadCallback.EVENT.invoker().interact(world.getBlockState(blockPos2).getBlock());
                            world.setBlockState(blockPos2, (BlockState)this.getDefaultState().with(getFacingProperty(direction3), true), 2);
                        } else {
                            Direction direction4 = direction.getOpposite();
                            if (bl && world.isAir(blockPos3) && shouldConnectTo(world, pos.offset(direction5), direction4)) {
                                BlockSpreadCallback.EVENT.invoker().interact(world.getBlockState(blockPos3).getBlock());
                                world.setBlockState(blockPos3, (BlockState)this.getDefaultState().with(getFacingProperty(direction4), true), 2);
                            } else if (bl2 && world.isAir(blockPos4) && shouldConnectTo(world, pos.offset(direction3), direction4)) {
                                BlockSpreadCallback.EVENT.invoker().interact(world.getBlockState(blockPos4).getBlock());
                                world.setBlockState(blockPos4, (BlockState)this.getDefaultState().with(getFacingProperty(direction4), true), 2);
                            } else if ((double)world.random.nextFloat() < 0.05D && shouldConnectTo(world, blockPos2.up(), Direction.UP)) {
                                BlockSpreadCallback.EVENT.invoker().interact(world.getBlockState(blockPos2).getBlock());
                                world.setBlockState(blockPos2, (BlockState)this.getDefaultState().with(UP, true), 2);
                            }
                        }
                    } else if (shouldConnectTo(world, blockPos2, direction)) {
                        world.setBlockState(pos, (BlockState)state.with(getFacingProperty(direction), true), 2);
                    }

                }
            } else {
                if (direction == Direction.UP && pos.getY() < 255) {
                    if (this.shouldHaveSide(world, pos, direction)) {
                        world.setBlockState(pos, (BlockState)state.with(UP, true), 2);
                        return;
                    }

                    if (world.isAir(blockPos)) {
                        if (!this.canGrowAt(world, pos)) {
                            return;
                        }

                        BlockState blockState2 = state;
                        Iterator var17 = Direction.Type.HORIZONTAL.iterator();

                        while(true) {
                            do {
                                if (!var17.hasNext()) {
                                    if (this.hasHorizontalSide(blockState2)) {
                                        PushHandler.getInstance().onBlockSpread();
                                        world.setBlockState(blockPos, blockState2, 2);
                                    }
                                    return;
                                }

                                direction5 = (Direction)var17.next();
                            } while(!random.nextBoolean() && shouldConnectTo(world, blockPos.offset(direction5), Direction.UP));

                            blockState2 = (BlockState)blockState2.with(getFacingProperty(direction5), false);
                        }
                    }
                }

                if (pos.getY() > 0) {
                    blockPos2 = pos.down();
                    blockState3 = world.getBlockState(blockPos2);
                    if (blockState3.isAir() || blockState3.isOf(this)) {
                        BlockState blockState4 = blockState3.isAir() ? this.getDefaultState() : blockState3;
                        BlockState blockState5 = this.getGrownState(state, blockState4, random);
                        if (blockState4 != blockState5 && this.hasHorizontalSide(blockState5)) {
                            BlockSpreadCallback.EVENT.invoker().interact(world.getBlockState(blockPos2).getBlock());
                            world.setBlockState(blockPos2, blockState5, 2);
                        }
                    }
                }
            }
        }
    }
}
