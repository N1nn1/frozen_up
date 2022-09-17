package com.ninni.frozenup.entity.ai.goal;

import com.ninni.frozenup.entity.PenguinEntity;
import com.ninni.frozenup.entity.PenguinMood;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.crafting.Ingredient;

public class PenguinTemptGoal extends TemptGoal {

    public PenguinTemptGoal(PathfinderMob penguin, double speed, Ingredient food, boolean canBeScared) {
        super(penguin, speed, food, canBeScared);
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof PenguinEntity penguin && penguin.hasEgg()) return false;
        return super.canUse();
    }

    @Override
    public void start() {
        super.start();
        if (this.mob instanceof PenguinEntity penguin) penguin.setMood(PenguinMood.CONFUSED);
    }
    @Override
    public void stop() {
        super.stop();
        if (this.mob instanceof PenguinEntity penguin) penguin.setMood(PenguinMood.HAPPY);
    }
}
