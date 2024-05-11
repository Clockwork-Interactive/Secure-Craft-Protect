package net.zeus.scpprotect.level.entity.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

import java.util.function.BooleanSupplier;

public class AnomalyWalkGoal extends WaterAvoidingRandomStrollGoal {

    private BooleanSupplier canUse = () -> true;
    private BooleanSupplier canContinueToUse = () -> true;
    
    public AnomalyWalkGoal(PathfinderMob mob, double speedModifier) {
        super(mob, speedModifier);
    }

    public AnomalyWalkGoal(PathfinderMob mob, double speedModifier, float probability) {
        super(mob, speedModifier, probability);
    }
    
    public AnomalyWalkGoal(PathfinderMob mob, double speedModifier, BooleanSupplier canUse, BooleanSupplier canContinueToUse) {
        super(mob, speedModifier);
        this.canUse = canUse;
        this.canContinueToUse = canContinueToUse;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.canUse.getAsBoolean();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.canContinueToUse.getAsBoolean();
    }
    
}
