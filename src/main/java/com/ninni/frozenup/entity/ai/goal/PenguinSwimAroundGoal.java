package com.ninni.frozenup.entity.ai.goal;

import com.ninni.frozenup.entity.PenguinEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;

public class PenguinSwimAroundGoal extends RandomSwimmingGoal {
    public PenguinSwimAroundGoal(PathfinderMob penguin, double d, int i) {
        super(penguin, d, i);
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof PenguinEntity penguin) if (penguin.hasEgg() || penguin.isPathFinding()) return false;
        return super.canUse();
    }
}
