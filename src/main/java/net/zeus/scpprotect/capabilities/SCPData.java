package net.zeus.scpprotect.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.refractionapi.refraction.capabilities.Data;
import net.zeus.scpprotect.SCP;

@AutoRegisterCapability
public class SCPData extends Data<SCPData> {

    public int candiesTaken = 0;

    @Override
    public void saveNBTData(CompoundTag compoundTag) {
        compoundTag.putInt("candiesTaken", this.candiesTaken);
    }

    @Override
    public void loadNBTData(CompoundTag compoundTag) {
        this.candiesTaken = compoundTag.getInt("candiesTaken");
    }

    @Override
    public void copyFromData(SCPData scpData) {
        this.candiesTaken = scpData.candiesTaken;
    }

}
