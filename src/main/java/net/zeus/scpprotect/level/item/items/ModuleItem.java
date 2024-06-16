package net.zeus.scpprotect.level.item.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.zeus.scpprotect.SCP;

public class ModuleItem extends Item {
    private final Component name;
    private final SCP.SCPTypes type;

    public ModuleItem(Properties pProperties, SCP.SCPTypes type, Component name) {
        super(pProperties);
        this.type = type;
        this.name = name;
    }

    public Component getName() {
        return this.name;
    }

    public SCP.SCPTypes getType() {
        return this.type;
    }
}
