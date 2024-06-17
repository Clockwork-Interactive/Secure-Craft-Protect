package net.zeus.scpprotect.level.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.DoorInteractGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.zeus.scpprotect.level.entity.entities.SCP096;
import net.zeus.scpprotect.util.Misc;

public class BreakBlockGoal096 extends DoorInteractGoal {
    protected int breakTime;
    protected int lastBreakProgress = -1;
    protected int doorBreakTime = -1;
    private SCP096 scp096;

    public BreakBlockGoal096(SCP096 pMob) {
        super(pMob);
    }

    public BreakBlockGoal096(SCP096 pMob, int pDoorBreakTime) {
        this(pMob);
        this.scp096 = pMob;
        this.doorBreakTime = pDoorBreakTime;
        ((GroundPathNavigation) this.scp096.getNavigation()).setCanOpenDoors(true);
        ((GroundPathNavigation) this.scp096.getNavigation()).setCanPassDoors(true);
    }

    protected int getDoorBreakTime() {
        return this.doorBreakTime;
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        if (!this.scp096.targets.isEmpty()) {
            if (!this.checkForBlock()) {
                return false;
            } else if (!this.scp096.canDestroy(this.doorPos)) {
                return false;
            } else {
                return !this.isOpen();
            }
        }
        return false;
    }

    public boolean checkForBlock() {
        GroundPathNavigation groundpathnavigation = (GroundPathNavigation) this.mob.getNavigation();
        Path path = groundpathnavigation.getPath();
        if (path != null && !path.isDone() && groundpathnavigation.canOpenDoors()) {
            for (int i = 0; i < Math.min(path.getNextNodeIndex() + 2, path.getNodeCount()); ++i) {
                Node node = path.getNode(i);
                for (int y = 0; y < 2; y++) {
                    this.doorPos = new BlockPos(node.x, node.y + y, node.z);
                    if (!(this.mob.distanceToSqr(this.doorPos.getX(), this.mob.getY(), this.doorPos.getZ()) > 2.25D)) {
                        this.hasDoor = this.scp096.canDestroy(this.doorPos);
                        if (this.hasDoor) {
                            return true;
                        }
                    }
                }
            }

            this.doorPos = this.mob.blockPosition();
            this.hasDoor = this.scp096.canDestroy(this.doorPos);
            return this.hasDoor;
        } else {
            return false;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        super.start();
        this.breakTime = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        return this.breakTime <= this.getDoorBreakTime() && !this.isOpen() && this.doorPos.closerToCenterThan(this.mob.position(), 2.0D) && !this.scp096.targets.isEmpty();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        super.stop();
        this.mob.level().destroyBlockProgress(this.mob.getId(), this.doorPos, -1);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        super.tick();
        if (this.mob.getRandom().nextInt(20) == 0) {
            this.mob.level().levelEvent(1019, this.doorPos, 0);
            if (!this.mob.swinging) {
                this.mob.swing(this.mob.getUsedItemHand());
            }
        }

        this.breakTime += 10;
        int i = (int) ((float) this.breakTime / (float) this.getDoorBreakTime() * 10.0F);
        if (i != this.lastBreakProgress) {
            this.mob.level().destroyBlockProgress(this.mob.getId(), this.doorPos, i);
            this.lastBreakProgress = i;
        }

        if (this.breakTime == this.getDoorBreakTime()) {
            this.mob.level().removeBlock(this.doorPos, false);
            this.mob.level().levelEvent(1021, this.doorPos, 0);
            this.mob.level().levelEvent(2001, this.doorPos, Block.getId(this.mob.level().getBlockState(this.doorPos)));
        }
    }

}
