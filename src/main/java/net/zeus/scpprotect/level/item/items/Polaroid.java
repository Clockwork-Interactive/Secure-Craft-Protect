package net.zeus.scpprotect.level.item.items;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zeus.scpprotect.level.entity.entities.SCP096;

public class Polaroid extends Item {

    public Polaroid(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        if (pPlayer.level() instanceof ServerLevel serverLevel) {
            SCP096 nearest = null;
            for (Entity entity : serverLevel.getEntities().getAll()) {
                if (entity instanceof SCP096 scp096 && entity.isAlive() && scp096.inRange(pPlayer)) {
                    nearest = scp096;
                    break;
                }
            }
            if (nearest != null) {
                nearest.lookForPlayerGoal.add(pPlayer);
            }
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
