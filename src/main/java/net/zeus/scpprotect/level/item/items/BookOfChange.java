package net.zeus.scpprotect.level.item.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zeus.scpprotect.level.entity.entities.SCP173;
import net.zeus.scpprotect.level.interfaces.DataGenObj;

public class BookOfChange extends TooltipItem implements DataGenObj {
    public BookOfChange(Properties pProperties) {
        super(pProperties, Component.translatable("tooltip.scprotect.awcy").withStyle(ChatFormatting.GRAY));
    }

    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return !pPlayer.isCreative();
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (pInteractionTarget instanceof SCP173 scp173) {
            scp173.setType((scp173.get173Type().ordinal() + 1) % SCP173.SCP173Types.values().length);
            pPlayer.displayClientMessage(Component.literal(scp173.get173Type().fullName), true);
            pPlayer.swing(pUsedHand);
            pPlayer.playSound(SoundEvents.BOOK_PAGE_TURN);
        }
        return super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pUsedHand);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (player.isCreative() && !(entity instanceof Player))
            entity.discard();
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public String customID() {
        return "AWCY? Book";
    }
}
