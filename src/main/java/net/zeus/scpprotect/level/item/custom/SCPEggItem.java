package net.zeus.scpprotect.level.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class SCPEggItem extends ForgeSpawnEggItem {
    private final SCP.SCPTypes eggType;
    public SCPEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props, SCP.SCPTypes types) {
        super(type, backgroundColor, highlightColor, props);
        this.eggType = types;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        switch (this.eggType) {
            case KETER -> pTooltipComponents.add(Component.literal("Keter").withStyle(ChatFormatting.RED));
            case EUCLID -> pTooltipComponents.add(Component.literal("Euclid").withStyle(ChatFormatting.YELLOW));
            case SAFE -> pTooltipComponents.add(Component.literal("Safe").withStyle(ChatFormatting.GREEN));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
