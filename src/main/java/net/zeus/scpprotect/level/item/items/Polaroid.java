package net.zeus.scpprotect.level.item.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zeus.scpprotect.level.entity.entities.SCP096;

public class Polaroid extends Item {

    public Polaroid(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof ServerPlayer serverPlayer && pIsSelected) {
            SCP096 nearest = null;
            for (Entity entity : serverPlayer.serverLevel().getEntities().getAll()) {
                if (entity instanceof SCP096 scp096 && entity.isAlive() && scp096.inRange(serverPlayer)) {
                    nearest = scp096;
                    break;
                }
            }
            if (nearest != null) {
                nearest.lookForPlayerGoal.add(serverPlayer);
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

}
