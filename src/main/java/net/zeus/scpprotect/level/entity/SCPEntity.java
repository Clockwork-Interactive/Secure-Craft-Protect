package net.zeus.scpprotect.level.entity;

import net.minecraft.world.entity.Entity;
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
    public static final RegistryObject<EntityType<ToxicSpit>> TOXIC_SPIT = register("toxic_spit",EntityType.Builder.of((EntityType.EntityFactory<ToxicSpit>) ToxicSpit::new, MobCategory.MISC).sized(0.2F, 0.2F));

    /**
     * SCP'S
     **/
    public static final RegistryObject<EntityType<SCP058>> SCP_058 = register("scp_058", EntityType.Builder.of(SCP058::new, MobCategory.MONSTER).sized(0.6F, 0.6F));

    public static final RegistryObject<EntityType<SCP096>> SCP_096 = register("scp_096", EntityType.Builder.of(SCP096::new, MobCategory.MONSTER).sized(0.5F, 1.99F).clientTrackingRange(1000));

    public static final RegistryObject<EntityType<SCP131>> SCP_131 = register("scp_131", EntityType.Builder.of(SCP131::new, MobCategory.MISC).sized(0.6F, 0.6F));

    public static final RegistryObject<EntityType<SCP173>> SCP_173 = register("scp_173", EntityType.Builder.of(SCP173::new, MobCategory.MONSTER).sized(0.5F, 1.99F));

    public static final RegistryObject<EntityType<SCP346>> SCP_346 = register("scp_346", EntityType.Builder.of(SCP346::new, MobCategory.MISC).sized(0.6F, 0.6F));

    public static final RegistryObject<EntityType<SCP939>> SCP_939 = register("scp_939", EntityType.Builder.of(SCP939::new, MobCategory.MONSTER).sized(1.4F, 1.3F));

    public static final RegistryObject<EntityType<SCP966>> SCP_966 = register("scp_966", EntityType.Builder.of(SCP966::new, MobCategory.MONSTER).sized(0.5F, 2.2F));

    public static final RegistryObject<EntityType<SCP999>> SCP_999 = register("scp_999", EntityType.Builder.of(SCP999::new, MobCategory.MISC).sized(0.6F, 0.6F));

    public static final RegistryObject<EntityType<SCP019_2>> SCP_019_2 = register("scp_019_2", EntityType.Builder.of(SCP019_2::new, MobCategory.MONSTER).sized(0.7F, 0.6F));

    public static final RegistryObject<EntityType<SCP811>> SCP_811 = register("scp_811", EntityType.Builder.of(SCP811::new, MobCategory.MONSTER).sized(0.5F, 1.8F));

    public static final RegistryObject<EntityType<SCP3199>> SCP_3199 = register("scp_3199", EntityType.Builder.of(SCP3199::new, MobCategory.MONSTER).sized(0.7F, 1.9F));

    public static final RegistryObject<EntityType<SCP3199Egg>> SCP_3199_EGG = register("scp_3199_egg", EntityType.Builder.of(SCP3199Egg::new, MobCategory.MONSTER).sized(0.2F, 0.4F));

    public static final RegistryObject<EntityType<SCP111>> SCP_111 = register("scp_111", EntityType.Builder.of(SCP111::new, MobCategory.CREATURE).sized(0.7F, 0.6F));

    public static final RegistryObject<EntityType<SCP049>> SCP_049 = register("scp_049", EntityType.Builder.of(SCP049::new, MobCategory.MONSTER).sized(0.6F, 1.99F));

    public static final RegistryObject<EntityType<SCP049_2>> SCP_049_2 = register("scp_049_2", EntityType.Builder.of(SCP049_2::new, MobCategory.MONSTER).sized(0.6F, 1.99F));

    /**
     * OTHER
     **/
    public static final RegistryObject<EntityType<Rebel>> REBEL = register("rebel", EntityType.Builder.of(Rebel::new, MobCategory.CREATURE).sized(0.6F, 0.65F));

    public static final RegistryObject<EntityType<ContainmentBox>> CONTAINMENT_BOX = register("containment_box", EntityType.Builder.of((EntityType.EntityFactory<ContainmentBox>) ContainmentBox::new, MobCategory.MISC).sized(1.2F, 2.75F));


    private static<E extends Entity> RegistryObject<EntityType<E>> register(String name, EntityType.Builder<E> builder) {
        return ENTITIES.register(name, () -> builder.build(name));
    }
}
