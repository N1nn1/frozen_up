package com.ninni.frozenup.entity.ai.goal;

import com.ninni.frozenup.entity.PenguinEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.registry.tag.FluidTags;

import java.util.EnumSet;

public class PenguinSwimGoal extends Goal {
    private final PenguinEntity penguin;

    public PenguinSwimGoal(PenguinEntity penguin) {
        this.penguin = penguin;
        this.setControls(EnumSet.of(Control.JUMP));
        penguin.getNavigation().setCanSwim(true);
    }

    @Override
    public boolean canStart() { return (this.penguin.hasEgg() && this.penguin.isTouchingWater() && this.penguin.getFluidHeight(FluidTags.WATER) > this.penguin.getSwimHeight() || this.penguin.isInLava()); }

    @Override
    public boolean shouldRunEveryTick() { return true; }

    @Override
    public void tick() { if (this.penguin.getRandom().nextFloat() < 0.8F)  this.penguin.getJumpControl().setActive(); }
}
