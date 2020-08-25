package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.callback.EntityChangeBlockCallback;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import cn.apisium.nekoguard.fabric.PushHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ComposterBlock.class)
public abstract class MixinComposterBlock_EntityChangeBlock  extends Block implements InventoryProvider {

    public MixinComposterBlock_EntityChangeBlock(Settings settings) {
        super(settings);
    }

    @Shadow
    public static BlockState emptyFullComposter(BlockState state, World world, BlockPos pos) {
        return null;
    }

    @Shadow
    protected static BlockState addToComposter(BlockState state, WorldAccess world, BlockPos pos, ItemStack item) {
        return null;
    }

    @Shadow @Final public static IntProperty LEVEL;

    @Shadow @Final public static Object2FloatMap<ItemConvertible> ITEM_TO_LEVEL_INCREASE_CHANCE;

    @Redirect(method = "compost", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/ComposterBlock;addToComposter(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/block/BlockState;"))
    private static BlockState onEntityChangeBlock(BlockState state, WorldAccess world, BlockPos pos, ItemStack item){
        EntityChangeBlockCallback.EVENT.invoker().interact(null, null);
        return addToComposter(state, world, pos, item);
    }

    /**
     * @author a
     * @reason a
     */
    @Overwrite
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int i = (Integer)state.get(LEVEL);
        ItemStack itemStack = player.getStackInHand(hand);
        if (i < 8 && ITEM_TO_LEVEL_INCREASE_CHANCE.containsKey(itemStack.getItem())) {
            if (i < 7 && !world.isClient) {
                BlockState blockState = addToComposter(state, world, pos, itemStack);
                world.syncWorldEvent(1500, pos, state != blockState ? 1 : 0);
                if (!player.abilities.creativeMode) {
                    itemStack.decrement(1);
                }
            }

            return ActionResult.success(world.isClient);
        } else if (i == 8) {
            EntityChangeBlockCallback.EVENT.invoker().interact(player, world.getBlockState(pos).getBlock());
            emptyFullComposter(state, world, pos);
            return ActionResult.success(world.isClient);
        } else {
            return ActionResult.PASS;
        }
    }
}
