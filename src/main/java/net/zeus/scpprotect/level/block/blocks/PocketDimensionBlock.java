package net.zeus.scpprotect.level.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.zeus.scpprotect.level.sound.SCPSoundTypes;
import org.jetbrains.annotations.Nullable;

public class PocketDimensionBlock extends Block {
    public PocketDimensionBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return SCPSoundTypes.PD_BLOCK;
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        pEntity.makeStuckInBlock(pState, new Vec3(0.7D, 0.07F, 0.7D));
    }

}
