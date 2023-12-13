package net.zeus.scpprotect.mixin;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zeus.scpprotect.level.effect.ModEffects;
import net.zeus.scpprotect.level.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, net.minecraftforge.common.extensions.IForgeLivingEntity {

    public LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot pSlot);

    @Inject(at = @At("HEAD"), method = "hasEffect", cancellable = true)
    public void hasInject(MobEffect pEffect, CallbackInfoReturnable<Boolean> cir) {
        if (pEffect == MobEffects.NIGHT_VISION && this.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.NODS.get())) {
            cir.setReturnValue(true);
        }
    }

}
