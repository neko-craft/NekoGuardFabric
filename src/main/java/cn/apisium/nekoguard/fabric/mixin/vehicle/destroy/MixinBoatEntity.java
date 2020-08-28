package cn.apisium.nekoguard.fabric.mixin.vehicle.destroy;

import cn.apisium.nekoguard.fabric.callback.VehicleDestroyCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public abstract class MixinBoatEntity extends Entity {

    public MixinBoatEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract float getDamageWobbleStrength();

    // EntityBoat 149
    // 太菜了不知道怎么 Inject 到 if 后面， 因此决定注入到 scheduleVelocityUpdate 后面，然后手动加 if 进行判断
    @Inject(method = "damage",
            at = @At(value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/entity/vehicle/BoatEntity;scheduleVelocityUpdate()V",
                    ordinal = 0))
    private void onVehicleDestroy(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        boolean bl = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity) source.getAttacker()).abilities.creativeMode;
        if (bl || this.getDamageWobbleStrength() > 40.0F) {
            VehicleDestroyCallback.EVENT.invoker().interact(this, source);
        }
    }

    // EntityBoat 790
    // 船从高处摔落时也会损毁
    @Inject(method = "fall",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/entity/vehicle/BoatEntity;remove()V"))
    private void onVehicleDestroy(double heightDifference, boolean onGround, BlockState landedState,
                                  BlockPos landedPosition, CallbackInfo ci) {
        VehicleDestroyCallback.EVENT.invoker().interact(this, DamageSource.FALL);
    }
}
