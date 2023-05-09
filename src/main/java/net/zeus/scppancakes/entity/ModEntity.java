package net.zeus.scppancakes.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scppancakes.SCPPancakes;
import net.zeus.scppancakes.entity.custom.SCP096;
import net.zeus.scppancakes.entity.custom.SCP173i;

public class ModEntity {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SCPPancakes.MOD_ID);

    public static final RegistryObject<EntityType<SCP096>> SCP_096 = ENTITY_TYPES.register("scp_096",
            () -> EntityType.Builder.of(SCP096::new, MobCategory.MONSTER).sized(SCP096.HITBOX_WIDTH, SCP096.HITBOX_HEIGHT).build(SCPPancakes.MOD_ID + ":scp_096"));

    public static final RegistryObject<EntityType<SCP173i>> SCP_173i = ENTITY_TYPES.register("scp_173i",
            () -> EntityType.Builder.of(SCP173i::new, MobCategory.MONSTER).sized(0.5F, 2.2F).build(SCPPancakes.MOD_ID + ":scp_173"));

    //public static final RegistryObject<EntityType<SCP939>> SCP_939 = ENTITY_TYPES.register("scp_173i",
    //        () -> EntityType.Builder.of(SCP939::new, MobCategory.MONSTER).sized(0.5F, 2.2F).build(SCPPancakes.MOD_ID + ":scp_939"));
//
    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
