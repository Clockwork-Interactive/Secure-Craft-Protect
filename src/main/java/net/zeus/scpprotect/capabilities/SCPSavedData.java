package net.zeus.scpprotect.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.refractionapi.refraction.capabilities.Data;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.configs.SCPServerConfig;
import net.zeus.scpprotect.level.interfaces.Anomaly;

import java.util.HashSet;
import java.util.Set;

@AutoRegisterCapability
public class SCPSavedData extends Data<SCPSavedData> {

    private Set<String> scps = new HashSet<>();

    public boolean hasSCP(EntityType<? extends Anomaly> scp) {;
        String id = transformRegistry(scp.getDescriptionId());
        return this.scps.contains(id) || SCPServerConfig.BLACKLISTED_SCPS.get().contains(id);
    }

    public void addSCP(EntityType<? extends Anomaly> scp) {
        this.scps.add(transformRegistry(scp.getDescriptionId()));
    }

    public void removeSCP(EntityType<? extends Anomaly> scp) {
        this.scps.remove(transformRegistry(scp.getDescriptionId()));
    }

    private String transformRegistry(String id) {
        return id.split("[.]")[2];
    }

    @Override
    public void saveNBTData(CompoundTag compoundTag) {
        compoundTag.putString("scps", String.join(",", this.scps));
    }

    @Override
    public void loadNBTData(CompoundTag compoundTag) {
        this.scps = new HashSet<>(Set.of(compoundTag.getString("scps").split(",")));
    }

    @Override
    public void copyFromData(SCPSavedData scpSavedData) {
        this.scps = scpSavedData.scps;
    }

}
