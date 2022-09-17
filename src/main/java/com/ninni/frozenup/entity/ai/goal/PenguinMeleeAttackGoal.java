package com.ninni.frozenup.entity.ai.goal;

import com.ninni.frozenup.entity.PenguinEntity;
import com.ninni.frozenup.entity.PenguinMood;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class PenguinMeleeAttackGoal extends MeleeAttackGoal {

    public PenguinMeleeAttackGoal(PathfinderMob penguin, double speed, boolean pauseWhenMobIdle) {
        super(penguin, speed, pauseWhenMobIdle);
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof PenguinEntity penguin) if (penguin.hasEgg() || penguin.isBaby()) return false;
        return super.canUse();
    }

    @Override
    public void start() {
        super.start();
        if (this.mob instanceof PenguinEntity penguin) penguin.setMood(PenguinMood.FOCUSED);
    }

    @Override
    public void stop() {
        super.stop();
        if (this.mob instanceof PenguinEntity penguin) penguin.setMood(PenguinMood.HAPPY);
    }
}
