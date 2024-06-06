package net.zeus.scpprotect.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.refractionapi.refraction.capabilities.Provider;

public class SCPSavedDataProvider extends Provider<SCPSavedData> {

    @Override
    protected Capability<SCPSavedData> getData() {
        return Capabilities.SCP_SAVED_DATA;
    }

    @Override
    public SCPSavedData build() {
        return new SCPSavedData();
    }

}
