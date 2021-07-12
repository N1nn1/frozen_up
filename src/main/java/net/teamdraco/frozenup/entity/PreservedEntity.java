package net.teamdraco.frozenup.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

import java.util.Random;


public class PreservedEntity extends ZombieEntity {
    public PreservedEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    public static boolean canSpawn(EntityType<PreservedEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return canSpawnInDark(type, world, spawnReason, pos, random) && (spawnReason == SpawnReason.SPAWNER || world.isSkyVisible(pos));
    }

    protected boolean burnsInDaylight() {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_HUSK_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_HUSK_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_HUSK_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_HUSK_STEP;
    }

    public boolean tryAttack(Entity target) {
        boolean bl = super.tryAttack(target);
        if (bl && this.getMainHandStack().isEmpty() && target instanceof LivingEntity) {
            float f = this.world.getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
            ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 140 * (int)f));
        }

        return bl;
    }

    protected boolean canConvertInWater() {
        return true;
    }

    protected void convertInWater() {
        this.convertTo(EntityType.DROWNED);
        if (!this.isSilent()) {
            this.world.syncWorldEvent(null, 1041, this.getBlockPos(), 0);
        }
    }

    protected ItemStack getSkull() {
        return ItemStack.EMPTY;
    }
}
