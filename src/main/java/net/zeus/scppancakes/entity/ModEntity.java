package net.zeus.scppancakes.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scppancakes.SCPPancakes;
import net.zeus.scppancakes.entity.custom.SCP096Entity;

public class ModEntity {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SCPPancakes.MOD_ID);

    public static final RegistryObject<EntityType<SCP096Entity>> SCP_096 = ENTITY_TYPES.register("scp_096",
            () -> EntityType.Builder.of(SCP096Entity::new, MobCategory.MONSTER).build(SCPPancakes.MOD_ID + ":scp_096"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
