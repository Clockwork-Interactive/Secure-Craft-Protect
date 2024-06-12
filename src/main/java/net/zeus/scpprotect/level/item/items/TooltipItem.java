package net.zeus.scpprotect.level.item.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TooltipItem extends Item {
    private final Component text;

    public TooltipItem(Properties pProperties, Component text) {
        super(pProperties);
        this.text = text;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Screen.hasShiftDown() || Screen.hasAltDown() ? this.text : Component.literal("Shift+ for Info").withStyle(ChatFormatting.BLUE));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
