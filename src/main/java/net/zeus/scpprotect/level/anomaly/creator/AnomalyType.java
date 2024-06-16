package net.zeus.scpprotect.level.anomaly.creator;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.anomaly.AnomalyRegistry;

import java.util.function.Supplier;

public abstract class AnomalyType<T, E> {
    protected final T type;
    private final SCP.SCPTypes scptypes;
    private final SCP.SCPNames scpnames;

    public AnomalyType(T type, SCP.SCPTypes scptypes, SCP.SCPNames scpNames) {
        this.type = type;
        this.scpnames = scpNames;
        this.scptypes = scptypes;
        AnomalyRegistry.ANOMALY_TYPES.put(this.type.toString(), this);
    }

    public abstract T createRaw(Level level, Vec3 pos);

    public abstract <R extends E> R create(Level level, Vec3 pos);

    public abstract <R extends E> R create(Level level);

    public <R extends E> R createContained(Level level, Vec3 pos) {
        R type = this.create(level, pos);
        if (type instanceof Entity entity) {
            ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.GLOWING, 20 * 15));
            ((Mob)entity).finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
            return type;
        }
        return type;
    }

    public String rawTypeString() {
        if (this.type instanceof Supplier<?> supplier)
            return supplier.get().toString();
        return this.type.toString();
    }

    public abstract ItemStack getItemized();

    public T getType() {
        return this.type;
    }

    public SCP.SCPTypes getClassType() {
        return this.scptypes;
    }

    public SCP.SCPNames getClassName() {
        return this.scpnames;
    }

    public static AnomalyType<?, ?> getAnomalyType(String name) {
        return AnomalyRegistry.ANOMALY_TYPES.getOrDefault(name, null);
    }
}
