package net.zeus.scpprotect.level.item.items;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.refractionapi.refraction.quest.QuestHandler;
import net.zeus.scpprotect.level.quest.LocateSCPQuest;
import net.zeus.scpprotect.tags.SCPItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class RealityScanner extends TooltipItem {
    public RealityScanner(Properties pProperties) {
        super(pProperties, Component.translatable("tooltip.scprotect.reality_scanner").withStyle(ChatFormatting.GRAY));
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

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
        if (pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
            if (pOther.isEmpty()) {
                remove(pStack).ifPresent(pAccess::set);
            } else if (pOther.is(SCPItemTags.MODULES)) {
                if (add(pStack, pOther)) {
                    pOther.shrink(1);
                }
            }
            return true;
        }
        return false;
    }

    private boolean add(ItemStack pStack, ItemStack pInsertedStack) {
        if (!pInsertedStack.isEmpty()) {
            CompoundTag tag = pStack.getOrCreateTag();
            if (!tag.contains("hasModule")) {
                tag.put("hasModule", new ListTag());
                ListTag modules = tag.getList("hasModule", 10);
                if (modules.isEmpty()) {
                    CompoundTag listTag = new CompoundTag();
                    pInsertedStack.save(listTag);
                    modules.add(0, listTag);
                    return true;
                }
            }
        }
        return false;
    }

    private Optional<ItemStack> remove(ItemStack pStack) {
        CompoundTag tag = pStack.getOrCreateTag();
        if (!tag.contains("hasModule")) {
            return Optional.empty();
        } else {
            ListTag modules = tag.getList("hasModule", 10);
            if (modules.isEmpty()) {
                return Optional.empty();
            } else {
                CompoundTag listTag = modules.getCompound(0);
                ItemStack moduleStack = ItemStack.of(listTag);
                modules.remove(0);
                if (modules.isEmpty()) {
                    pStack.removeTagKey("hasModule");
                }
                return Optional.of(moduleStack);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getOrCreateTag();
        ItemStack stack = ItemStack.of(tag.getList("hasModule", 10).getCompound(0));
        pTooltipComponents.add(Component.literal(tag.contains("hasModule") ? "Module inserted: " + ((ModuleItem)stack.getItem()).getName().getString() : "No module inserted").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
