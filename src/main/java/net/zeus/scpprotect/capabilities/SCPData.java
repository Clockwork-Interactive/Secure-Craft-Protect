package net.zeus.scpprotect.capabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.refractionapi.refraction.capabilities.Data;

@AutoRegisterCapability
public class SCPData extends Data<SCPData> {

    public int candiesTaken = 0;
    public BlockPos scp106TakenPos = BlockPos.ZERO;
    public ResourceLocation scp106TakenDim = new ResourceLocation("overworld");

    @Override
    public void saveNBTData(CompoundTag compoundTag) {
        compoundTag.putInt("candiesTaken", this.candiesTaken);
        compoundTag.putInt("scp106TakenX", this.scp106TakenPos.getX());
        compoundTag.putInt("scp106TakenY", this.scp106TakenPos.getY());
        compoundTag.putInt("scp106TakenZ", this.scp106TakenPos.getZ());
        compoundTag.putString("scp106TakenDim", this.scp106TakenDim.toString());
    }

    @Override
    public void loadNBTData(CompoundTag compoundTag) {
        this.candiesTaken = compoundTag.getInt("candiesTaken");
        this.scp106TakenPos = new BlockPos(compoundTag.getInt("scp106TakenX"), compoundTag.getInt("scp106TakenY"), compoundTag.getInt("scp106TakenZ"));
        this.scp106TakenDim = new ResourceLocation(compoundTag.getString("scp106TakenDim"));
    }

    @Override
    public void copyFromData(SCPData scpData) {
        this.candiesTaken = scpData.candiesTaken;
        this.scp106TakenPos = scpData.scp106TakenPos;
        this.scp106TakenDim = scpData.scp106TakenDim;
    }

}
