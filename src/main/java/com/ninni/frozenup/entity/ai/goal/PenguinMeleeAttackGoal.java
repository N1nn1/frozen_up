package com.ninni.frozenup.entity.ai.goal;

import com.ninni.frozenup.entity.PenguinEntity;
import com.ninni.frozenup.entity.PenguinMood;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class PenguinMeleeAttackGoal extends MeleeAttackGoal {
    public PenguinMeleeAttackGoal(PathAwareEntity penguin, double speed, boolean pauseWhenMobIdle) {
        super(penguin, speed, pauseWhenMobIdle);
    }

    @Override
    public boolean canStart() {
        if (this.mob instanceof PenguinEntity penguin) if (penguin.hasEgg() || penguin.isBaby()) return false;
        return super.canStart();
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
