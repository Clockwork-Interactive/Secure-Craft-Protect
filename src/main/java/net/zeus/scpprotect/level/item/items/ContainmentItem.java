package net.zeus.scpprotect.level.item.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.zeus.scpprotect.level.entity.misc.ContainmentBox;

public class ContainmentItem extends Item {
    public ContainmentItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (pPlayer.level().isClientSide) return InteractionResult.FAIL;

        if (pInteractionTarget instanceof LivingEntity && !(pInteractionTarget instanceof Player || pInteractionTarget instanceof ContainmentBox)) {

            ContainmentBox containmentBox = new ContainmentBox(pPlayer.level());
            containmentBox.setPos(pInteractionTarget.getX(), pInteractionTarget.getY(), pInteractionTarget.getZ());

            AABB entityHitbox = pInteractionTarget.getBoundingBox();
            AABB containmentHitbox = containmentBox.getBoundingBox();

            if (entityHitbox.getYsize() > containmentHitbox.getYsize() || entityHitbox.getXsize() > containmentHitbox.getXsize() || entityHitbox.getZsize() > containmentHitbox.getZsize()) {
                containmentBox.discard();
                return InteractionResult.FAIL;
            }

            containmentBox.containEntity(pInteractionTarget);

            pInteractionTarget.discard();
            pPlayer.level().addFreshEntity(containmentBox);
            return InteractionResult.SUCCESS;
        }

        return super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pUsedHand);
    }
}
