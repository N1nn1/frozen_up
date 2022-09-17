package com.ninni.frozenup.entity.ai.goal;

import com.ninni.frozenup.entity.PenguinEntity;
import com.ninni.frozenup.entity.PenguinMood;
import com.ninni.frozenup.init.FrozenUpSoundEvents;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class PenguinSlideGoal extends WaterAvoidingRandomStrollGoal {

    public PenguinSlideGoal(PathfinderMob penguin, double speedModifier) {
        super(penguin, speedModifier);
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, 10, 0) : super.getPosition();
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof PenguinEntity penguin && penguin.hasEgg() || mob.isUnderWater() || this.mob.isPathFinding() || this.mob.getVehicle() != null) return false;
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (this.mob.isUnderWater()) return false;
        return super.canContinueToUse();
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
        if (this.mob instanceof PenguinEntity penguin) {
            penguin.setMood(PenguinMood.FOCUSED);
            penguin.setSliding(true);
            penguin.playSound(FrozenUpSoundEvents.ENTITY_PENGUIN_SLIDE.get(), 0.5F, 1);
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
