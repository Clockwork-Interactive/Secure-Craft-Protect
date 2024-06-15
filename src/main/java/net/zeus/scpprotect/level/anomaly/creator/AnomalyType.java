package net.zeus.scpprotect.level.anomaly.creator;

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
        return this.create(level, pos);
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
