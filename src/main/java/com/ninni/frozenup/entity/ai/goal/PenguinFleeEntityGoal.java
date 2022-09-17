package com.ninni.frozenup.entity.ai.goal;

import com.ninni.frozenup.entity.PenguinEntity;
import com.ninni.frozenup.entity.PenguinMood;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

public class PenguinFleeEntityGoal extends AvoidEntityGoal {

    public PenguinFleeEntityGoal(PathfinderMob penguin, Class fleeFromType, float distance, double slowSpeed, double fastSpeed) {
        super(penguin, fleeFromType, distance, slowSpeed, fastSpeed);
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof PenguinEntity penguin && penguin.hasEgg()) return false;
        return super.canUse();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.mob instanceof PenguinEntity penguin) penguin.setMood(PenguinMood.AGITATED);
    }

    @Override
    public void stop() {
        super.stop();
        if (this.mob instanceof PenguinEntity penguin) penguin.setMood(PenguinMood.HAPPY);
    }
}
