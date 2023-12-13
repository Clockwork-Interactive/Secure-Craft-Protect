package net.zeus.scpprotect.level.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.refractionapi.refraction.registry.block.BaseEntityBlock;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.ModBlockEntities;
import net.zeus.scpprotect.level.effect.ModEffects;

public class SCP310BlockEntity extends BaseEntityBlock {

    public SCP310BlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SCP_310_BE.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide) return;
        AABB range = new AABB(new BlockPos(this.getBlockPos().getX() - 2, this.getBlockPos().getY() - 2, this.getBlockPos().getZ() - 2), new BlockPos(this.getBlockPos().getX() + 2, this.getBlockPos().getY() + 2, this.getBlockPos().getZ() + 2));
        if (this.tickCount % 10 == 0) {
            ((ServerLevel) level).sendParticles(ParticleTypes.SMALL_FLAME, this.getBlockPos().getX() + 0.5F, this.getBlockPos().getY() + 0.35F, this.getBlockPos().getZ() + 0.5F, 1, 0.0F, 0.0F, 0, 0);
        }
        for (Entity entity : this.level.getEntities(null, range)) {
            if (!(entity instanceof LivingEntity livingEntity)) return;
            if (this.tickCount % 20 == 0) {
                if (livingEntity instanceof ServerPlayer serverPlayer && (serverPlayer.isCreative() || serverPlayer.isSpectator()))
                    return;
                if (livingEntity.hasEffect(ModEffects.UNEXTINGUISHABLE.get())) return;
                if (livingEntity.getRandom().nextFloat() > 0.90F)
                    livingEntity.addEffect(new MobEffectInstance(ModEffects.UNEXTINGUISHABLE.get(), -1, 0));
            }
        }
    }

}
