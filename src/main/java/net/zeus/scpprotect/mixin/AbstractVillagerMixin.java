package net.zeus.scpprotect.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerMixin extends AgeableMob {

    protected AbstractVillagerMixin(EntityType<? extends AgeableMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "finalizeSpawn", at = @At("HEAD"))
    public void define(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, SpawnGroupData pSpawnData, CompoundTag pDataTag, CallbackInfoReturnable<SpawnGroupData> cir) {
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, LivingEntity.class, (livingEntity ->
                livingEntity instanceof Anomaly anomaly && !anomaly.getClassType().equals(SCP.SCPTypes.SAFE)), 16, this.getAttributeValue(Attributes.MOVEMENT_SPEED) + 0.1F, this.getAttributeValue(Attributes.MOVEMENT_SPEED) + 0.1F, (livingEntity -> true)));
    }

}
