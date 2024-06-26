package net.zeus.scpprotect.level.item.scp;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.advancements.SCPAdvancements;
import net.zeus.scpprotect.level.effect.SCPEffects;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.item.SCPItems;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class SCP1025 extends Item implements Anomaly {
    public SCP1025(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pLevel.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0F, 0.9F);
        pPlayer.swing(pUsedHand);

        if (pLevel instanceof ServerLevel) {
            MobEffect pick = ForgeRegistries.MOB_EFFECTS.getEntries().stream().filter(effect ->
                    !effect.getValue().isBeneficial() && pPlayer.getRandom().nextFloat() >= 0.9F && SCPEffects.MOB_EFFECTS.getEntries().stream().noneMatch(e -> {
                        assert e.getKey() != null;
                        return e.getKey().equals(effect.getKey());
                    })
            ).findAny().map(Map.Entry::getValue).orElse(MobEffects.CONFUSION);
            SCPAdvancements.grant(pPlayer, SCPAdvancements.POTENTIAL_BIOWEAPON);
            pPlayer.getCooldowns().addCooldown(SCPItems.SCP_1025.get(), 40);
            pPlayer.addEffect(new MobEffectInstance(pick, RandomSource.create().nextInt(400, 600)));
            pPlayer.displayClientMessage(Component.literal("You read a page on ").append(Component.translatable(pick.getDescriptionId())).append("..."), true);
            return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
        }
        return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public SCP.SCPNames getSCPName() {
        return SCP.SCPNames.SCP_1025;
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.SAFE;
    }
}
