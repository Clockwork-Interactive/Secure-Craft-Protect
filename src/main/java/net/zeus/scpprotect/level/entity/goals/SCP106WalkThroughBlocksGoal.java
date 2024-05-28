package net.zeus.scpprotect.level.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.zeus.scpprotect.level.entity.entities.SCP106;

public class SCP106WalkThroughBlocksGoal extends Goal {

    public final SCP106 scp106;
    public BlockPos blockPos = BlockPos.ZERO;
    public boolean hasBlock;
    public boolean passed;
    public float blockDirX;
    public float blockDirZ;

    public SCP106WalkThroughBlocksGoal(SCP106 scp106) {
        this.scp106 = scp106;
        GroundPathNavigation navigation = (GroundPathNavigation) this.scp106.getNavigation();
        navigation.setCanPassDoors(true);
        navigation.setCanOpenDoors(true);
    }

    @Override
    public boolean canUse() {
        if (this.hasBlockInPath()) {
            this.scp106.noPhysics = true;
            return true;
        }
        return false;
    }

    public boolean hasBlockInPath() {
        if (!this.scp106.horizontalCollision || this.scp106.getTarget() == null) {
            return false;
        } else {
            GroundPathNavigation groundpathnavigation = (GroundPathNavigation) this.scp106.getNavigation();
            Path path = groundpathnavigation.getPath();
            if (path != null && !path.isDone() && groundpathnavigation.canOpenDoors()) {
                for (int i = 0; i < Math.min(path.getNextNodeIndex() + 2, path.getNodeCount()); ++i) {
                    Node node = path.getNode(i);
                    this.blockPos = new BlockPos(node.x, node.y, node.z);
                    if (!(this.scp106.distanceToSqr(this.blockPos.getX(), this.scp106.getY(), this.blockPos.getZ()) > 2.0D)) {
                        this.hasBlock = !this.scp106.level().getBlockState(this.blockPos).isAir();
                        if (this.hasBlock) {
                            return true;
                        }
                    }
                }

                this.blockPos = this.scp106.blockPosition();
                this.hasBlock = !this.scp106.level().getBlockState(this.blockPos).isAir();
                return this.hasBlock;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.passed;
    }

    @Override
    public void start() {
        this.passed = false;
        this.blockDirX = (float) ((double) this.blockPos.getX() + 0.5D - this.scp106.getX());
        this.blockDirZ = (float) ((double) this.blockPos.getZ() + 0.5D - this.scp106.getZ());
    }

    @Override
    public void stop() {
        this.scp106.noPhysics = false;
        this.scp106.setNoGravity(false);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }


    public void tick() {
        float f = (float) ((double) this.blockPos.getX() + 0.5D - this.scp106.getX());
        float f1 = (float) ((double) this.blockPos.getZ() + 0.5D - this.scp106.getZ());
        float f2 = this.blockDirX * f + this.blockDirZ * f1;
        if (f2 < 0.0F || f2 > 0.5F) {
            this.passed = true;
        }

    }
}