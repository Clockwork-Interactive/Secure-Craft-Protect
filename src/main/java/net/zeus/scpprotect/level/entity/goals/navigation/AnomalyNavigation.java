package net.zeus.scpprotect.level.entity.goals.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.zeus.scpprotect.level.entity.goals.node.UniversalDoorNodeEval;

public class AnomalyNavigation extends GroundPathNavigation {

    public AnomalyNavigation(Mob pMob, Level pLevel) {
        super(pMob, pLevel);
    }

    @Override
    protected PathFinder createPathFinder(int pMaxVisitedNodes) {
        this.nodeEvaluator = new UniversalDoorNodeEval();
        this.nodeEvaluator.setCanPassDoors(true);
        this.nodeEvaluator.setCanOpenDoors(true);
        return new PathFinder(this.nodeEvaluator, pMaxVisitedNodes);
    }

}
