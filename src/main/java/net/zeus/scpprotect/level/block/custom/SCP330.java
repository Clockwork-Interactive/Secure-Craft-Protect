package net.zeus.scpprotect.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.refractionapi.refraction.misc.RefractionMisc;
import net.refractionapi.refraction.registry.block.BaseHorizontalEntityBlock;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.block.SCPVoxelShapes;
import net.zeus.scpprotect.level.block.entity.custom.SCP330BlockEntity;
import net.zeus.scpprotect.level.effect.SCPEffects;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.item.SCPItems;
import net.zeus.scpprotect.level.sound.SCPSounds;
import net.zeus.scpprotect.networking.ModMessages;
import net.zeus.scpprotect.networking.S2C.VignetteS2CPacket;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SCP330 extends BaseHorizontalEntityBlock implements Anomaly {

    public SCP330(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide || pHand == InteractionHand.OFF_HAND) return InteractionResult.FAIL;

        SCP330BlockEntity scp330BlockEntity = (SCP330BlockEntity) pLevel.getBlockEntity(pPos);
        if (scp330BlockEntity == null) return InteractionResult.FAIL;

        scp330BlockEntity.candiesTaken.putIfAbsent(pPlayer, 0);
        scp330BlockEntity.candiesTaken.put(pPlayer, scp330BlockEntity.candiesTaken.get(pPlayer) + 1);

        pPlayer.getInventory().add(new ItemStack(RefractionMisc.getRandom(List.of(SCPItems.CANDY_RED.get(), SCPItems.CANDY_BLUE.get(), SCPItems.CANDY_GREEN.get(), SCPItems.CANDY_YELLOW.get(), SCPItems.CANDY_ORANGE.get(), SCPItems.CANDY_PURPLE.get())), 1));

        if (scp330BlockEntity.candiesTaken.get(pPlayer) > 2) {
            scp330BlockEntity.candiesTaken.put(pPlayer, 0);
            pPlayer.addEffect(new MobEffectInstance(SCPEffects.AMPUTATED.get(), 250, 0, false, true, true));
            pPlayer.level().playSound(null, pPlayer.blockPosition(), SCPSounds.SCP_330_SEVER.get(), pPlayer.getSoundSource(), 1.0F, 1.0F);
            if (pPlayer instanceof ServerPlayer player)
                ModMessages.sendToPlayer(new VignetteS2CPacket(250, true, false), player);
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SCPVoxelShapes.SCP_330;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SCP330BlockEntity(pPos, pState);
    }

    @Override
    public SCP.SCPTypes getClassType() {
        return SCP.SCPTypes.SAFE;
    }
}
