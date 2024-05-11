package net.zeus.scpprotect.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class Capabilities {

    public static Capability<SCPData> SCP_DATA = CapabilityManager.get(new CapabilityToken<>() {});

}
