package net.zeus.scpprotect.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.refractionapi.refraction.capabilities.Provider;

public class SCPDataProvider extends Provider<SCPData> {

    @Override
    protected Capability<SCPData> getData() {
        return Capabilities.SCP_DATA;
    }

    @Override
    public SCPData build() {
        return new SCPData();
    }

}
