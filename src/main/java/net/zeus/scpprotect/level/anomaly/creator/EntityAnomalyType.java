package net.zeus.scpprotect.level.anomaly.creator;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.anomaly.AnomalyRegistry;

import java.util.Objects;
import java.util.function.Supplier;

public class EntityAnomalyType<E extends Entity> extends AnomalyType<Supplier<EntityType<E>>, E> {

    private final int spawnCount;

    public EntityAnomalyType(Supplier<EntityType<E>> type, SCP.SCPTypes scptypes, SCP.SCPNames scpNames) {
        this(type, scptypes, scpNames, 1);
    }

    public EntityAnomalyType(Supplier<EntityType<E>> type, SCP.SCPTypes scptypes, SCP.SCPNames scpNames, int spawnCount) {
        super(type, scptypes, scpNames);
        this.spawnCount = spawnCount;
    }

    @Override
    public Supplier<EntityType<E>> createRaw(Level level, Vec3 pos) {
        Entity entity = this.create(level, pos);
        if (entity != null) {
            level.addFreshEntity(entity);
        }
        return this.type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R extends E> R create(Level level, Vec3 pos) {
        Entity entity = this.create(level);
        if (entity != null) {
            entity.teleportTo(pos.x, pos.y, pos.z);
            level.addFreshEntity(entity);
        }
        return (R) entity;
    }

    @Override
    public <R extends E> R createContained(Level level, Vec3 pos) {
        if (this.spawnCount > 1) {
            int spawnCount = level.random.nextInt(this.spawnCount);
            for (int i = 0; i < spawnCount; i++) {
                super.createContained(level, pos);
            }
        }
        return super.createContained(level, pos);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R extends E> R create(Level level) {
        return (R) this.type.get().create(level);
    }

    @Override
    public ItemStack getItemized() {
        Item item = ForgeSpawnEggItem.fromEntityType(this.type.get());
        return item != null ? new ItemStack(item) : ItemStack.EMPTY;
    }

    @SuppressWarnings("unchecked")
    public static <H extends Entity> EntityAnomalyType<H> getAnomalyType(EntityType<H> name) {
        return AnomalyRegistry.ANOMALY_TYPES.values()
                .stream()
                .filter(registry -> Objects.equals(registry.rawTypeString(), name.toString()))
                .map(anomalyType -> (EntityAnomalyType<H>) anomalyType)
                .findFirst()
                .orElse(null);
    }



}
