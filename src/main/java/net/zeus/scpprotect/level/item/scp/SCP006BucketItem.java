package net.zeus.scpprotect.level.item.scp;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.material.Fluid;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.interfaces.DataGenObj;

import java.util.function.Supplier;

public class SCP006BucketItem extends BucketItem implements Anomaly, DataGenObj {
    public SCP006BucketItem(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder);
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.SAFE;
    }

    @Override
    public SCP.SCPNames getSCPName() {
        return SCP.SCPNames.SCP_006;
    }

    @Override
    public String customID() {
        return "SCP-006 Bucket";
    }
}
