package net.zeus.scpprotect.level.entity.goals.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.zeus.scpprotect.level.entity.goals.node.SCP096NodeEvaluator;

public class SCP096ClimbingNavigation extends WallClimberNavigation {

    public SCP096ClimbingNavigation(Mob pMob, Level pLevel) {
        super(pMob, pLevel);
    }

    @Override
    protected PathFinder createPathFinder(int pMaxVisitedNodes) {
        this.nodeEvaluator = new SCP096NodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        this.nodeEvaluator.setCanOpenDoors(true);
        return new PathFinder(this.nodeEvaluator, pMaxVisitedNodes);
    }

}
