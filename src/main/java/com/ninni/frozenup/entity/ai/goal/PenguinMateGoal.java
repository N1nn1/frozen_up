package com.ninni.frozenup.entity.ai.goal;

import com.ninni.frozenup.entity.PenguinEntity;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;

 public class PenguinMateGoal extends AnimalMateGoal {
    private final PenguinEntity penguin;

    public PenguinMateGoal(PenguinEntity penguin, double speed) {
        super(penguin, speed);
        this.penguin = penguin;
    }

    @Override
    public boolean canStart() {
        if (this.penguin.hasEgg() || !this.penguin.isOnGround()) return false;
        return super.canStart();
    }

    @Override
    protected void breed() {
        ServerPlayerEntity serverPlayerEntity = this.animal.getLovingPlayer();
        assert this.mate != null;

        if (serverPlayerEntity == null && this.mate.getLovingPlayer() != null) {
            serverPlayerEntity = this.mate.getLovingPlayer();
        }
        if (serverPlayerEntity != null) {
            serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
            Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, this.animal, this.mate, null);
        }

        this.penguin.setBreedingAge(6000);
        this.animal.setBreedingAge(6000);
        this.penguin.setEggTicks(5 * 60 * 20); //Five minutes
        this.penguin.setHasEgg(true);
        this.animal.resetLoveTicks();
        this.mate.resetLoveTicks();
        Random random = this.animal.getRandom();
        if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
        }

    }
}