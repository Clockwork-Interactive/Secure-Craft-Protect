package net.zeus.scpprotect.mixin;

import net.minecraft.commands.CommandSource;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.entity.EntityAccess;
import net.zeus.scpprotect.level.effect.ModEffects;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

@Mixin(Entity.class)
public abstract class EntityMixin extends net.minecraftforge.common.capabilities.CapabilityProvider<Entity> implements Nameable, EntityAccess, CommandSource, net.minecraftforge.common.extensions.IForgeEntity {

    @Shadow
    public abstract @NotNull Stream<Entity> getSelfAndPassengers();

    @Shadow
    private int remainingFireTicks;

    protected EntityMixin(Class<Entity> baseClass) {
        super(baseClass);
    }

    @Inject(at = @At("HEAD"), method = "shouldRender", cancellable = true)
    public void renderMixin(double pX, double pY, double pZ, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof Anomaly) {
            cir.setReturnValue(true);
        }
    }


    @Inject(at = @At("HEAD"), method = "shouldRenderAtSqrDistance", cancellable = true)
    public void renderSqrMixin(double pDistance, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof Anomaly) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "extinguishFire", cancellable = true)
    public void clearInject(CallbackInfo ci) {
        if (this.getSelfAndPassengers().findFirst().isPresent() && this.getSelfAndPassengers().findFirst().get() instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(ModEffects.UNEXTINGUISHABLE.get())) {
                ci.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "playEntityOnFireExtinguishedSound", cancellable = true)
    public void soundInject(CallbackInfo ci) {
        if (this.getSelfAndPassengers().findFirst().isPresent() && this.getSelfAndPassengers().findFirst().get() instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(ModEffects.UNEXTINGUISHABLE.get())) {
                ci.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "setRemainingFireTicks", cancellable = true)
    public void fireInject(int pSecondsOnFire, CallbackInfo ci) {
        if (this.getSelfAndPassengers().findFirst().isPresent() && this.getSelfAndPassengers().findFirst().get() instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(ModEffects.UNEXTINGUISHABLE.get())) {
                ci.cancel();
                this.remainingFireTicks = 40;
            }
        }
    }

}
