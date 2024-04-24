package net.zeus.scpprotect.level.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;

public class SCPFluidTypes {
    private static final ResourceLocation SCP_006_STILL = new ResourceLocation(SCP.MOD_ID, "block/scp_006_still");
    private static final ResourceLocation SCP_006_FLOWING = new ResourceLocation(SCP.MOD_ID, "block/scp_006_flow");

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, SCP.MOD_ID);

    public static final RegistryObject<FluidType> SCP_006_FLUID_TYPE = FLUID_TYPES.register("scp_006", () -> new BaseSCPFluidType(FluidType.Properties.create()
            .fallDistanceModifier(0.0F)
            .canExtinguish(true)
            .canConvertToSource(true)
            .supportsBoating(true)
            .canHydrate(true)
            .adjacentPathType(BlockPathTypes.WATER)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH), true, SCP_006_STILL, SCP_006_FLOWING, 0x8047b2ff));
}
