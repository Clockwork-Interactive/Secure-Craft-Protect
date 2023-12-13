package net.zeus.scpprotect.level.entity.custom.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import net.refractionapi.refraction.vec3.Vec3Helper;
import net.zeus.scpprotect.util.Misc;
import net.zeus.scpprotect.level.entity.custom.SCP939;

import javax.annotation.Nullable;

public class SCP939ListenTargetGoal extends TargetGoal {

    @Nullable
    protected LivingEntity target;
    /**
     * This filter is applied to the Entity search. Only matching entities will be targeted.
     */
    protected TargetingConditions targetConditions;

    public SCP939ListenTargetGoal(SCP939 scp939) {
        super(scp939, false);
        this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(livingEntity -> true);
    }

    public boolean canUse() {
        this.findTarget();
        return this.target != null;
    }

    protected AABB getTargetSearchArea() {
        return this.mob.getBoundingBox().inflate(10);
    }

    protected void findTarget() {
        LivingEntity livingEntity = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(LivingEntity.class, this.getTargetSearchArea(), (val) -> true), this.targetConditions, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());
        if (livingEntity != null && livingEntity != this.mob) {
            if (Vec3Helper.isEntityMoving(livingEntity) && !livingEntity.isCrouching()) {
                this.target = livingEntity;
                this.mob.setTarget(livingEntity);
            } else if (livingEntity.distanceTo(this.mob) < 4.0F) {
                this.target = livingEntity;
                this.mob.setTarget(livingEntity);
            }
        }
    }

}
