package net.zeus.scpprotect.level.item.scp;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.interfaces.DataGenObj;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

import static net.refractionapi.refraction.event.CommonForgeEvents.tag;

public class SCP207 extends Item implements Anomaly, DataGenObj {
    private static final String TAG_SIPS = "Sips";
    static int sips = tag.getInt("Sips");
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
        if (pLivingEntity instanceof Player player && player.getAbilities().instabuild && getSips(pStack) != 4) {
            player.displayClientMessage(Component.literal("Sips: " + getSips(pStack)), true);
            setSips(pStack, ++sips);
        } else {
            pStack.shrink(1);
        }

        return pStack.isEmpty() ? new ItemStack(Items.GLASS_BOTTLE) : pStack;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }

    public static int getSips(ItemStack pItemStack) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.getInt("Sips");
        pItemStack.setTag(compoundTag);
        return sips;
    }

    public void setSips(ItemStack pItemStack, int pSips) {
        CompoundTag tag = pItemStack.getOrCreateTag();
        if (!tag.contains("Sips")) tag.putInt("Sips", 0);
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
