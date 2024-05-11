package net.zeus.scpprotect.level.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import oshi.util.tuples.Pair;

import java.util.HashMap;

public class PacificationEffect extends MobEffect {
    public static final HashMap<LivingEntity, Pair<Integer, Goal>> savedGoals = new HashMap<>(); // Eh

    public PacificationEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity instanceof Mob mob) {
            for (WrappedGoal goal : mob.goalSelector.getAvailableGoals()) {
                if (goal.getGoal() instanceof MeleeAttackGoal) {
                    savedGoals.put(mob, new Pair<>(goal.getPriority(), goal.getGoal()));
                    mob.goalSelector.removeGoal(goal.getGoal());
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    public static void onRemove(LivingEntity entity) {
        if (entity instanceof Mob mob && PacificationEffect.savedGoals.containsKey(mob)) {
            Pair<Integer, Goal> pair = PacificationEffect.savedGoals.get(mob);
            mob.goalSelector.addGoal(pair.getA(), pair.getB());
            savedGoals.remove(mob);
        }
    }

}
