package net.zeus.scpprotect.level.item.scp;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.interfaces.DataGenObj;
import org.jetbrains.annotations.NotNull;

import static net.refractionapi.refraction.event.CommonForgeEvents.tag;

public class SCP207 extends Item implements Anomaly, DataGenObj {
    private int sips;

    public SCP207(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 32;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        CompoundTag tag = pStack.getOrCreateTag();
        if (!tag.contains("sips")) tag.putInt("sips", 0);
        sips = tag.getInt("sips");
        if (pLivingEntity instanceof Player player && !player.getAbilities().instabuild && !(tag.getInt("sips") == 3)) {
            tag.putInt("sips", tag.getInt("sips") + 1);
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, sips));
            if (tag.getInt("sips") == 3)
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 600, 1));
        } else if (tag.getInt("sips") == 3) {
            pStack.shrink(1);
        }

        return pStack.isEmpty() ? new ItemStack(Items.GLASS_BOTTLE) : pStack;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }

    @Override
    public String customID() {
        return "SCP-207 Bottle";
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.EUCLID;
    }
}
