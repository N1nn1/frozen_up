package com.ninni.frozenup.entity.ai.goal;

import com.ninni.frozenup.entity.PenguinEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class PenguinWanderAroundFarGoal extends WaterAvoidingRandomStrollGoal {

    public PenguinWanderAroundFarGoal(PathfinderMob penguin, double speed) {
        super(penguin, speed);
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof PenguinEntity penguin) { if (penguin.hasEgg() || penguin.isSliding() || mob.isUnderWater() || penguin.isPathFinding()) return false; }
        return super.canUse();
    }


}
