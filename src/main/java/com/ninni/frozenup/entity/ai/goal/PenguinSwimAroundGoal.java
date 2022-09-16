package com.ninni.frozenup.entity.ai.goal;

import com.ninni.frozenup.entity.PenguinEntity;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class PenguinSwimAroundGoal extends SwimAroundGoal {
    public PenguinSwimAroundGoal(PathAwareEntity penguin, double d, int i) {
        super(penguin, d, i);
    }

    @Override
    public boolean canStart() {
        if (this.mob instanceof PenguinEntity penguin) if (penguin.hasEgg() || penguin.isNavigating()) return false;
        return super.canStart();
    }
}
