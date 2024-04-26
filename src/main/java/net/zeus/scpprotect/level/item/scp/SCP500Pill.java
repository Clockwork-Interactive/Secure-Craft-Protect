package net.zeus.scpprotect.level.item.scp;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.interfaces.DataGenObj;
import net.zeus.scpprotect.level.item.SCPItems;

import java.util.Map;

public class SCP500Pill extends Item implements Anomaly, DataGenObj {
    public SCP500Pill(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 6;
    }

    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        if (!pLevel.isClientSide) {
            pEntityLiving.curePotionEffects(pStack);
        }

        if (pEntityLiving instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, pStack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (pEntityLiving instanceof Player && !((Player)pEntityLiving).getAbilities().instabuild) {
            ((Player) pEntityLiving).getCooldowns().addCooldown(SCPItems.SCP_500.get(), 40);
            pStack.shrink(1);
        }

        Map<MobEffect, MobEffectInstance> effects = pEntityLiving.getActiveEffectsMap();
        effects.entrySet().removeIf(mobEffectMobEffectInstanceEntry -> !mobEffectMobEffectInstanceEntry.getKey().isBeneficial());


        return super.finishUsingItem(pStack, pLevel, pEntityLiving);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }

    @Override
    public String customID() {
        return "SCP-500 Pill";
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.SAFE;
    }
}
