package com.ninni.frozenup.entity.ai.goal;

import com.ninni.frozenup.entity.PenguinEntity;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class PenguinWanderAroundFarGoal extends WanderAroundFarGoal {

    public PenguinWanderAroundFarGoal(PathAwareEntity penguin, double speed) {
        super(penguin, speed);
    }

    @Override
    public boolean canStart() {
        if (this.mob instanceof PenguinEntity penguin) { if (penguin.hasEgg() || penguin.isSliding() || mob.isSubmergedInWater() || penguin.isNavigating()) return false; }
        return super.canStart();
    }
}
