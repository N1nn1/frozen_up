package com.ninni.frozenup.entity.ai.goal;

import com.ninni.frozenup.entity.PenguinEntity;
import com.ninni.frozenup.entity.PenguinMood;
import com.ninni.frozenup.sound.FrozenUpSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Formatting;

public class PenguinLookAtEntityGoal  extends LookAtEntityGoal {
    public PenguinLookAtEntityGoal(MobEntity penguin, Class<? extends LivingEntity> targetType, float range) {
        super(penguin, targetType, range);
    }

    @Override
    public boolean canStart() {
        if (this.mob instanceof PenguinEntity penguin && penguin.isSliding()) return false;
        return super.canStart();
    }

    @Override
    public void start() {
        super.start();
        if ("Dwayne".equals(Formatting.strip(mob.getName().getString()))) { mob.playSound(FrozenUpSoundEvents.ENTITY_PENGUIN_BOOM, 0.35F, 1);}
        if (this.mob instanceof PenguinEntity penguin) penguin.setMood(PenguinMood.CONFUSED);
    }
    @Override
    public void stop() {
        super.stop();
        if (this.mob instanceof PenguinEntity penguin) penguin.setMood(PenguinMood.HAPPY);
    }
}
