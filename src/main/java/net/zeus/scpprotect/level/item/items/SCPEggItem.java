package net.zeus.scpprotect.level.item.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.anomaly.creator.EntityAnomalyType;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class SCPEggItem extends ForgeSpawnEggItem {
    private SCP.SCPTypes eggType;
    private SCP.SCPNames eggName;

    public SCPEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (this.eggType == null && this.eggName == null) {
            this.eggType = EntityAnomalyType.getAnomalyType(this.getType(null)).getClassType();
            this.eggName = EntityAnomalyType.getAnomalyType(this.getType(null)).getClassName();
        }
        if (eggType != SCP.SCPTypes.UNCLASSIFIED) {
            pTooltipComponents.add(this.eggType.component);
        }
        if (eggName != SCP.SCPNames.UNDEFINED) {
            pTooltipComponents.add(this.eggName.component);
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
