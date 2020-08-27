package cn.apisium.nekoguard.fabric.mixin.entity.interact;

import cn.apisium.nekoguard.fabric.callback.EntityInteractCallback;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallMountedBlock;
import net.minecraft.entity.Entity;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(AbstractButtonBlock.class)
public abstract class MixinAbstractButtonBlock extends WallMountedBlock {

    @Shadow
    @Final
    public static BooleanProperty POWERED;

    public MixinAbstractButtonBlock(Settings settings) {
        super(settings);
    }

    @Inject(method = "tryPowerWithProjectiles",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/state/State;get(Lnet/minecraft/state/property/Property;)Ljava/lang/Comparable;",
                    ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private void onTryPowerWithProjectiles(BlockState state, World world, BlockPos pos, CallbackInfo info,
                                           List<Entity> list, boolean bl, boolean bl2) {
        if (bl2 != bl && bl) {
            for (Entity entity : list) {
                if (entity != null) {
                    EntityInteractCallback.EVENT.invoker().interact(entity, pos);
                    break;
                }
            }
        }
    }
}
