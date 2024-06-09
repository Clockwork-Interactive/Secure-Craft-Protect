package net.zeus.scpprotect.level.fluid;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.FacilityBlocks;
import net.zeus.scpprotect.level.block.SCPBlocks;
import net.zeus.scpprotect.level.item.SCPItems;

public class SCPFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, SCP.MOD_ID);

    public static final RegistryObject<FlowingFluid> SOURCE_SCP_006 = FLUIDS.register("scp_006", () -> new ForgeFlowingFluid.Source(SCPFluids.SCP_006_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_SCP_006 = FLUIDS.register("flowing_scp_006", () -> new ForgeFlowingFluid.Flowing(SCPFluids.SCP_006_PROPERTIES));

    public static final ForgeFlowingFluid.Properties SCP_006_PROPERTIES = new ForgeFlowingFluid.Properties(SCPFluidTypes.SCP_006_FLUID_TYPE, SOURCE_SCP_006, FLOWING_SCP_006)
            .block(FacilityBlocks.SCP_006)
            .bucket(SCPItems.SCP_006_BUCKET);
}
