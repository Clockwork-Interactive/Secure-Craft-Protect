package net.zeus.scpprotect.level.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.custom.*;
import net.zeus.scpprotect.level.entity.custom.projectiles.ToxicSpit;

public class ModEntity {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SCP.MOD_ID);

    /**
     * Projectiles
     **/

    public static final RegistryObject<EntityType<ToxicSpit>> TOXIC_SPIT = ENTITIES.register("toxic_spit",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ToxicSpit>) ToxicSpit::new, MobCategory.MISC).sized(0.2F, 0.2F).build("toxic_spit"));

    /**
     * SCP'S
     **/

    public static final RegistryObject<EntityType<SCP096>> SCP_096 = ENTITIES.register("scp_096",
            () -> EntityType.Builder.of(SCP096::new, MobCategory.MONSTER).sized(SCP096.HITBOX_WIDTH, SCP096.HITBOX_HEIGHT).build(SCP.MOD_ID + ":scp_096"));

    public static final RegistryObject<EntityType<SCP173i>> SCP_173i = ENTITIES.register("scp_173i",
            () -> EntityType.Builder.of(SCP173i::new, MobCategory.MONSTER).sized(0.5F, 1.8F).build(SCP.MOD_ID + ":scp_173"));

    public static final RegistryObject<EntityType<SCP939>> SCP_939 = ENTITIES.register("scp_939",
            () -> EntityType.Builder.of(SCP939::new, MobCategory.MONSTER).sized(0.5F, 1.5F).build(SCP.MOD_ID + ":scp_939"));

    public static final RegistryObject<EntityType<SCP966>> SCP_966 = ENTITIES.register("scp_966",
            () -> EntityType.Builder.of(SCP966::new, MobCategory.MONSTER).sized(0.5F, 2.2F).build(SCP.MOD_ID + ":scp_966"));

    public static final RegistryObject<EntityType<SCP019_2>> SCP_019_2 = ENTITIES.register("scp_019_2",
            () -> EntityType.Builder.of(SCP019_2::new, MobCategory.MONSTER).sized(0.7F, 0.6F).build(SCP.MOD_ID + ":scp_019_2"));

    public static final RegistryObject<EntityType<SCP811>> SCP_811 = ENTITIES.register("scp_811",
            () -> EntityType.Builder.of(SCP811::new, MobCategory.MONSTER).sized(0.5F, 1.8F).build(SCP.MOD_ID + ":scp_811"));


}
