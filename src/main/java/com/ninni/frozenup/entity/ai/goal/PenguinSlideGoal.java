package com.ninni.frozenup.entity.ai.goal;

import com.ninni.frozenup.entity.PenguinEntity;
import com.ninni.frozenup.entity.PenguinMood;
import com.ninni.frozenup.sound.FrozenUpSoundEvents;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class PenguinSlideGoal extends WanderAroundFarGoal {
    public PenguinSlideGoal(PathAwareEntity penguin, double speed) {
        super(penguin, speed);
    }

    @Override
    @Nullable
    protected Vec3d getWanderTarget() { return this.mob.getRandom().nextFloat() >= this.probability ? FuzzyTargeting.find(this.mob, 10, 0) : super.getWanderTarget(); }

    @Override
    public boolean canStart() {
        if (this.mob instanceof PenguinEntity penguin && penguin.hasEgg() || mob.isSubmergedInWater() || this.mob.isNavigating() || this.mob.getVehicle() != null) return false;
        return super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        if(this.mob.isSubmergedInWater()) return false;
        return super.shouldContinue();
    }

    @Override
    public void start() {
        this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
        if (this.mob instanceof PenguinEntity penguin) {
            penguin.setMood(PenguinMood.FOCUSED);
            penguin.setSliding(true);
            penguin.playSound(FrozenUpSoundEvents.ENTITY_PENGUIN_SLIDE, 0.5F, 1);
        }
    }

    @Override
    public void stop() {
        super.stop();
        if (this.mob instanceof PenguinEntity penguin) {
            penguin.setMood(PenguinMood.HAPPY);
            penguin.setSliding(false);
        }
    }
}
