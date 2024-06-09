package net.zeus.scpprotect.level.item.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.refractionapi.refraction.quest.QuestHandler;
import net.zeus.scpprotect.level.quest.LocateSCPQuest;

public class RealityScanner extends Item {

    public RealityScanner(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        CompoundTag tag = stack.getOrCreateTag();
        if (pLevel.isClientSide || !pLevel.dimensionTypeId().equals(BuiltinDimensionTypes.OVERWORLD)) return super.use(pLevel, pPlayer, pUsedHand);
        if (!tag.contains("quest") && pPlayer instanceof ServerPlayer serverPlayer) {
            LocateSCPQuest quest = new LocateSCPQuest(serverPlayer, stack, null);
            CompoundTag questTag = new CompoundTag();
            quest.serializeNBT(questTag);
            tag.put("quest", questTag);
        } else if (tag.contains("quest") && pPlayer instanceof ServerPlayer serverPlayer) {
            if (QuestHandler.QUESTS.get(serverPlayer.getUUID()) == null)
                new LocateSCPQuest(serverPlayer, stack, tag.getCompound("quest"));
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }


}
