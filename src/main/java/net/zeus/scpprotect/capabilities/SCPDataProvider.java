package net.zeus.scpprotect.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SCPDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<SCPData> SCP_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });

    private SCPData scpdata = null;
    private final LazyOptional<SCPData> optional = LazyOptional.of(this::createSCPData);

    private SCPData createSCPData() {
        if(this.scpdata == null) {
            this.scpdata = new SCPData();
        }
        return this.scpdata;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == SCP_DATA ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createSCPData().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createSCPData().loadNBTData(nbt);
    }
}
