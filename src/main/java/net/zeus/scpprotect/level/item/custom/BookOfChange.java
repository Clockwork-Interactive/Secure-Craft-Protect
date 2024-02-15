package net.zeus.scpprotect.level.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.zeus.scpprotect.level.entity.entities.SCP173;

public class BookOfChange extends Item {

    public BookOfChange(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (pInteractionTarget instanceof SCP173 scp173) {
            scp173.setType(scp173.get173Type().ordinal() + 1 % SCP173.SCP173Types.values().length);
            pPlayer.displayClientMessage(Component.literal(scp173.get173Type().fullName), true);
        }
        return super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pUsedHand);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        entity.discard();
        return super.onLeftClickEntity(stack, player, entity);
    }
}
