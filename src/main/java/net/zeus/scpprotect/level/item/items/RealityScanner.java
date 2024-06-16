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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.refractionapi.refraction.quest.QuestHandler;
import net.zeus.scpprotect.level.quest.LocateSCPQuest;
import net.zeus.scpprotect.tags.SCPItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class RealityScanner extends TooltipItem {
    public static final int MAX_WEIGHT = 1;

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

    // rest of this code has been copied from the bundle class
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        if (pStack.getCount() != 1 || pAction != ClickAction.SECONDARY) {
            return false;
        } else {
            ItemStack stack = pSlot.getItem();
            if (stack.isEmpty()) {
                removeOne(pStack).ifPresent((p_150740_) -> {
                    add(pStack, pSlot.safeInsert(p_150740_));
                });
            } else if (stack.getItem().canFitInsideContainerItems() && stack.is(SCPItemTags.MODULES)) {
                int i = (MAX_WEIGHT - getContentWeight(pStack)) / getWeight(stack);
                int j = add(pStack, pSlot.safeTake(stack.getCount(), i, pPlayer));
            }
            return true;
        }
    }

    public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
        if (pStack.getCount() != 1) return false;
        if (pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
            if (pOther.isEmpty()) {
                removeOne(pStack).ifPresent(pAccess::set);

            } else if (pOther.getItem().canFitInsideContainerItems() && pOther.is(SCPItemTags.MODULES)) {
                int i = add(pStack, pOther);
                if (i > 0) {
                    pOther.shrink(i);
                }
            }
            return true;

        } else {
            return false;
        }
    }

    public static int add(ItemStack pStack, ItemStack pInsertedStack) {
        if (!pInsertedStack.isEmpty() && pInsertedStack.getItem().canFitInsideContainerItems()) {
            CompoundTag tag = pStack.getOrCreateTag();
            if (!tag.contains("hasModule")) {
                tag.put("hasModule", new ListTag());
            }

            int i = getContentWeight(pStack);
            int j = getWeight(pInsertedStack);
            int k = Math.min(pInsertedStack.getCount(), (MAX_WEIGHT - i) / j);
            if (k == 0) {
                return 0;
            } else {
                ListTag listtag = tag.getList("hasModule", 10);
                Optional<CompoundTag> optional = getMatchingItem(pInsertedStack, listtag);
                if (optional.isPresent()) {
                    CompoundTag tag1 = optional.get();
                    ItemStack stack = ItemStack.of(tag1);
                    stack.grow(k);
                    stack.save(tag1);
                    listtag.remove(tag1);
                    listtag.add(0, tag1);
                } else {
                    ItemStack stack = pInsertedStack.copyWithCount(k);
                    CompoundTag tag2 = new CompoundTag();
                    stack.save(tag2);
                    listtag.add(0, tag2);
                }

                return k;
            }
        } else {
            return 0;
        }
    }

    public static Optional<CompoundTag> getMatchingItem(ItemStack pStack, ListTag pList) {
        return pStack.is(Items.BUNDLE) ? Optional.empty() : pList.stream().filter(CompoundTag.class::isInstance).map(CompoundTag.class::cast).filter((p_186350_) -> {
            return ItemStack.isSameItemSameTags(ItemStack.of(p_186350_), pStack);
        }).findFirst();
    }

    public static int getWeight(ItemStack pStack) {
        if (pStack.is(Items.BUNDLE)) {
            return 4 + getContentWeight(pStack);
        } else {
            return MAX_WEIGHT / pStack.getMaxStackSize();
        }
    }

    public static int getContentWeight(ItemStack pStack) {
        return getContents(pStack).mapToInt((p_186356_) -> {
            return getWeight(p_186356_) * p_186356_.getCount();
        }).sum();
    }

    public static Optional<ItemStack> removeOne(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        if (!compoundtag.contains("hasModule")) {
            return Optional.empty();
        } else {
            ListTag listtag = compoundtag.getList("hasModule", 10);
            if (listtag.isEmpty()) {
                return Optional.empty();
            } else {
                int i = 0;
                CompoundTag compoundtag1 = listtag.getCompound(0);
                ItemStack itemstack = ItemStack.of(compoundtag1);
                listtag.remove(0);
                if (listtag.isEmpty()) {
                    pStack.removeTagKey("hasModule");
                }

                return Optional.of(itemstack);
            }
        }
    }

    public static Stream<ItemStack> getContents(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTag();
        if (compoundtag == null) {
            return Stream.empty();
        } else {
            ListTag listtag = compoundtag.getList("hasModule", 10);
            return listtag.stream().map(CompoundTag.class::cast).map(ItemStack::of);
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
