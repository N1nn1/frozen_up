package com.ninni.frozenup.entity.ai.goal;

import com.ninni.frozenup.entity.PenguinEntity;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.level.GameRules;

 public class PenguinMateGoal extends BreedGoal {
    private final PenguinEntity penguin;

    public PenguinMateGoal(PenguinEntity penguin, double speed) {
        super(penguin, speed);
        this.penguin = penguin;
    }

     @Override
     public boolean canUse() {
         if (this.penguin.hasEgg() || !this.penguin.onGround()) return false;
         return super.canUse();
     }

     @Override
    protected void breed() {
        ServerPlayer serverPlayerEntity = this.animal.getLoveCause();
        assert this.partner != null;

        if (serverPlayerEntity == null && this.partner.getLoveCause() != null) {
            serverPlayerEntity = this.partner.getLoveCause();
        }
        if (serverPlayerEntity != null) {
            serverPlayerEntity.awardStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(serverPlayerEntity, this.animal, this.partner, null);
        }

        this.penguin.setAge(6000);
        this.animal.setAge(6000);
        this.penguin.setEggTicks(5 * 60 * 20); //Five minutes
        this.penguin.setHasEgg(true);
        this.animal.resetLove();
        this.partner.resetLove();
        RandomSource random = this.animal.getRandom();
        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
        }

    }
}