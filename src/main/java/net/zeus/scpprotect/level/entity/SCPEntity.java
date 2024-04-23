package net.zeus.scpprotect.level.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.entity.entities.*;
import net.zeus.scpprotect.level.entity.entities.SCP173;
import net.zeus.scpprotect.level.entity.misc.ContainmentBox;
import net.zeus.scpprotect.level.entity.projectiles.ToxicSpit;

public class SCPEntity {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SCP.MOD_ID);

    /**
     * Projectiles
     **/

    public static final RegistryObject<EntityType<ToxicSpit>> TOXIC_SPIT = ENTITIES.register("toxic_spit",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ToxicSpit>) ToxicSpit::new, MobCategory.MISC).sized(0.2F, 0.2F).build("toxic_spit"));

    /**
     * SCP'S
     **/

    public static final RegistryObject<EntityType<SCP058>> SCP_058 = ENTITIES.register("scp_058",
            () -> EntityType.Builder.of(SCP058::new, MobCategory.MONSTER).sized(0.6F, 0.6F).build(SCP.MOD_ID + ":scp_058"));

    public static final RegistryObject<EntityType<SCP096>> SCP_096 = ENTITIES.register("scp_096",
            () -> EntityType.Builder.of(SCP096::new, MobCategory.MONSTER).sized(0.5F, 1.82F).clientTrackingRange(1000).build("scp_096"));

    public static final RegistryObject<EntityType<SCP173>> SCP_173 = ENTITIES.register("scp_173",
            () -> EntityType.Builder.of(SCP173::new, MobCategory.MONSTER).sized(0.5F, 1.8F).build("scp_173"));

    public static final RegistryObject<EntityType<SCP939>> SCP_939 = ENTITIES.register("scp_939",
            () -> EntityType.Builder.of(SCP939::new, MobCategory.MONSTER).sized(1.4F, 1.3F).build("scp_939"));

    public static final RegistryObject<EntityType<SCP966>> SCP_966 = ENTITIES.register("scp_966",
            () -> EntityType.Builder.of(SCP966::new, MobCategory.MONSTER).sized(0.5F, 2.2F).build("scp_966"));

    public static final RegistryObject<EntityType<SCP019_2>> SCP_019_2 = ENTITIES.register("scp_019_2",
            () -> EntityType.Builder.of(SCP019_2::new, MobCategory.MONSTER).sized(0.7F, 0.6F).build("scp_019_2"));

    public static final RegistryObject<EntityType<SCP811>> SCP_811 = ENTITIES.register("scp_811",
            () -> EntityType.Builder.of(SCP811::new, MobCategory.MONSTER).sized(0.5F, 1.8F).build("scp_811"));

    public static final RegistryObject<EntityType<SCP3199>> SCP_3199 = ENTITIES.register("scp_3199",
            () -> EntityType.Builder.of(SCP3199::new, MobCategory.MONSTER).sized(0.7F, 1.9F).build("scp_3199"));

    public static final RegistryObject<EntityType<SCP3199Egg>> SCP_3199_EGG = ENTITIES.register("scp_3199_egg",
            () -> EntityType.Builder.of(SCP3199Egg::new, MobCategory.MONSTER).sized(0.2F, 0.4F).build("scp_3199_egg"));

    public static final RegistryObject<EntityType<SCP111>> SCP_111 = ENTITIES.register("scp_111",
            () -> EntityType.Builder.of(SCP111::new, MobCategory.CREATURE).sized(0.7F, 0.6F).build("scp_111"));
    
    /**
     * OTHER
     **/
    
    public static final RegistryObject<EntityType<Rebel>> REBEL = ENTITIES.register("rebel",
            () -> EntityType.Builder.of(Rebel::new, MobCategory.CREATURE).sized(0.6F, 0.65F).build("rebel"));

    public static final RegistryObject<EntityType<ContainmentBox>> CONTAINMENT_BOX = ENTITIES.register("containment_box",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ContainmentBox>) ContainmentBox::new, MobCategory.MISC).sized(1.2F, 2.75F).build("containment_box"));

}
