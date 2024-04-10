package net.zeus.scpprotect.level.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;

public class SCPParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SCP.MOD_ID);

    public static final RegistryObject<SimpleParticleType> BLOOD = PARTICLES.register("blood", () -> new SimpleParticleType(true));

}
