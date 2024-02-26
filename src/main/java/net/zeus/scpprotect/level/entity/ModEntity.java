package net.zeus.scpprotect.level.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.*;
import net.zeus.scpprotect.level.entity.entities.SCP173;
import net.zeus.scpprotect.level.entity.entities.projectiles.ToxicSpit;

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
            () -> EntityType.Builder.of(SCP096::new, MobCategory.MONSTER).sized(0.5F, 1.82F).clientTrackingRange(1000).build(SCP.MOD_ID + ":scp_096"));

    public static final RegistryObject<EntityType<SCP173>> SCP_173 = ENTITIES.register("scp_173",
            () -> EntityType.Builder.of(SCP173::new, MobCategory.MONSTER).sized(0.5F, 1.8F).build(SCP.MOD_ID + ":scp_173"));

    public static final RegistryObject<EntityType<SCP939>> SCP_939 = ENTITIES.register("scp_939",
            () -> EntityType.Builder.of(SCP939::new, MobCategory.MONSTER).sized(1.4F, 1.3F).build(SCP.MOD_ID + ":scp_939"));

    public static final RegistryObject<EntityType<SCP966>> SCP_966 = ENTITIES.register("scp_966",
            () -> EntityType.Builder.of(SCP966::new, MobCategory.MONSTER).sized(0.5F, 2.2F).build(SCP.MOD_ID + ":scp_966"));

    public static final RegistryObject<EntityType<SCP019_2>> SCP_019_2 = ENTITIES.register("scp_019_2",
            () -> EntityType.Builder.of(SCP019_2::new, MobCategory.MONSTER).sized(0.7F, 0.6F).build(SCP.MOD_ID + ":scp_019_2"));

    public static final RegistryObject<EntityType<SCP811>> SCP_811 = ENTITIES.register("scp_811",
            () -> EntityType.Builder.of(SCP811::new, MobCategory.MONSTER).sized(0.5F, 1.8F).build(SCP.MOD_ID + ":scp_811"));

    public static final RegistryObject<EntityType<SCP3199>> SCP_3199 = ENTITIES.register("scp_3199",
            () -> EntityType.Builder.of(SCP3199::new, MobCategory.MONSTER).sized(0.7F, 1.9F).build(SCP.MOD_ID + ":scp_3199"));

    public static final RegistryObject<EntityType<SCP3199Egg>> SCP_3199_EGG = ENTITIES.register("scp_3199_egg",
            () -> EntityType.Builder.of(SCP3199Egg::new, MobCategory.MONSTER).sized(0.2F, 0.4F).build(SCP.MOD_ID + ":scp_3199_egg"));

    /**
     * OTHER
     **/

    public static final RegistryObject<EntityType<ContainmentBox>> CONTAINMENT_BOX = ENTITIES.register("containment_box",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ContainmentBox>) ContainmentBox::new, MobCategory.MISC).sized(1.2F, 2.75F).build(SCP.MOD_ID + ":containment_box"));

}
