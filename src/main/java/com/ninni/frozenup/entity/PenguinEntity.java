package com.ninni.frozenup.entity;

import com.ninni.frozenup.entity.ai.goal.PenguinEscapeDangerGoal;
import com.ninni.frozenup.entity.ai.goal.PenguinFleeEntityGoal;
import com.ninni.frozenup.entity.ai.goal.PenguinLookAtEntityGoal;
import com.ninni.frozenup.entity.ai.goal.PenguinMateGoal;
import com.ninni.frozenup.entity.ai.goal.PenguinMeleeAttackGoal;
import com.ninni.frozenup.entity.ai.goal.PenguinSlideGoal;
import com.ninni.frozenup.entity.ai.goal.PenguinSwimAroundGoal;
import com.ninni.frozenup.entity.ai.goal.PenguinSwimGoal;
import com.ninni.frozenup.entity.ai.goal.PenguinTemptGoal;
import com.ninni.frozenup.entity.ai.goal.PenguinWanderAroundFarGoal;
import com.ninni.frozenup.init.FrozenUpBlockTags;
import com.ninni.frozenup.init.FrozenUpEntities;
import com.ninni.frozenup.init.FrozenUpSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PenguinEntity extends Animal {
    private static final EntityDataAccessor<Integer> MOOD = SynchedEntityData.defineId(PenguinEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(PenguinEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> EGG_TICKS = SynchedEntityData.defineId(PenguinEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SLIDING = SynchedEntityData.defineId(PenguinEntity.class, EntityDataSerializers.BOOLEAN);
    public static final Ingredient TEMPT_INGREDIENT = Ingredient.of(ItemTags.FISHES);
    public int WingsFlapTicks;

    public PenguinEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new PenguinMoveControl(this);
    }

    @Override
    public float maxUpStep() {
        return 1.0F;
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Cod.class, false));

        this.goalSelector.addGoal(0,  new PenguinMeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(0,  new PenguinSwimGoal(this));
        this.goalSelector.addGoal(1,  new PenguinMateGoal(this, 1.0));
        this.goalSelector.addGoal(2,  new PenguinFleeEntityGoal(this, PolarBear.class, 8.0F, 1.2, 1.5));
        this.goalSelector.addGoal(3,  new PenguinFleeEntityGoal(this, Wolf.class, 6.0F, 1.2, 1.5));
        this.goalSelector.addGoal(4,  new PenguinFleeEntityGoal(this, Fox.class, 7.0F, 1.2, 1.5));
        this.goalSelector.addGoal(5,  new PenguinFleeEntityGoal(this, Cat.class, 6.0F, 1.2, 1.5));
        this.goalSelector.addGoal(6,  new PenguinEscapeDangerGoal(this, 1.4));
        this.goalSelector.addGoal(7,  new FollowParentGoal(this, 1.2));
        this.goalSelector.addGoal(8,  new PenguinTemptGoal(this, 1.1, TEMPT_INGREDIENT, false));
        this.goalSelector.addGoal(9,  new PenguinSwimAroundGoal(this, 1, 40));
        this.goalSelector.addGoal(10, new PenguinWanderAroundFarGoal(this, 0.8));
        this.goalSelector.addGoal(11, new PenguinSlideGoal(this, 1.8));
        this.goalSelector.addGoal(12, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(13, new PenguinLookAtEntityGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(14, new PenguinLookAtEntityGoal(this, PenguinEntity.class, 6.0F));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData data, @Nullable CompoundTag entityNbt) {
        AgeableMobGroupData passiveData = (AgeableMobGroupData)data;
        if (passiveData != null && passiveData.getGroupSize() > 0 && this.random.nextFloat() <= passiveData.getBabySpawnChance()) this.setHasEgg(true);
        return super.finalizeSpawn(world, difficulty, spawnReason, data, entityNbt);
    }

    public static AttributeSupplier.Builder createPenguinAttributes() {
        return createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.2).add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(ItemTags.FISHES);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected int calculateFallDamage(float pDistance, float pDamageMultiplier) {
        return super.calculateFallDamage(pDistance, pDamageMultiplier) - 5;
    }

    private void flapWing() { this.WingsFlapTicks = 1; }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.hasEgg()) this.setDeltaMovement(this.getDeltaMovement().scale(0.6D));
        if (this.isEffectiveAi() && this.isUnderWater()) {
            this.moveRelative(this.getSpeed(), pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.0025D, 0.0D));
        } else { super.travel(pTravelVector); }
    }

    @Override
    public float getEyeHeightAccess(Pose pose, EntityDimensions size) {
        return size.height * 0.75F;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.random.nextInt(200) == 0) {
            this.flapWing();
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.WingsFlapTicks > 0 && ++this.WingsFlapTicks > 8) { this.WingsFlapTicks = 0; }

        if (this.getMood() == PenguinMood.AGITATED) {
            for(int i = 0; i < 1; ++i) {
                double velocityX = this.random.nextGaussian() * -5;
                double velocityY = this.random.nextGaussian() * -5;
                double velocityZ = this.random.nextGaussian() * -5;
                this.level().addParticle(ParticleTypes.SPLASH, this.getRandomX(0.5), this.getRandomY() + 0.5, this.getRandomZ(0.5), velocityX, velocityY, velocityZ);
            }
        }

        if (this.hasEgg()) setEggTicks(getEggTicks() - 1);

        if (this.getEggTicks() == 0 && !this.isNoAi()) {
            setHasEgg(false);
            this.playSound(FrozenUpSoundEvents.ENTITY_PENGUIN_HATCH.get(), 1, 1);
            Optional.ofNullable(FrozenUpEntities.PENGUIN.get().create(level())).ifPresent(entity -> {
                entity.setAge(-24000);
                entity.moveTo(this.blockPosition().getX(), this.blockPosition().getY(), this.blockPosition().getZ(), 0.0F, 0.0F);
                level().addFreshEntity(entity);
            });
            setEggTicks(1);
        }

        if (this.isSliding() && !this.isInWater()) {
            for(int i = 0; i < 1; ++i) {
                double velocityX = this.random.nextGaussian() * 0.15;
                double velocityY = this.random.nextGaussian() * 0.15;
                double velocityZ = this.random.nextGaussian() * 0.15;
                this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn()), this.getRandomX(1), this.getRandomY() - 0.5, this.getRandomZ(1) - 0.75, velocityX, velocityY, velocityZ);
            }
        }
        if (this.wasEyeInWater && this.isPathFinding() && !this.hasEgg()){
            for(int i = 0; i < 1; ++i) {
                double velocityX = this.random.nextGaussian() * 0.15;
                double velocityY = this.random.nextGaussian() * 0.15;
                double velocityZ = this.random.nextGaussian() * 0.15;
                this.level().addParticle(ParticleTypes.BUBBLE, this.getRandomX(1), this.getRandomY() - 0.5, this.getRandomZ(1) - 0.75, velocityX, velocityY, velocityZ);
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MOOD, 0);
        this.entityData.define(HAS_EGG, false);
        this.entityData.define(EGG_TICKS, 1);
        this.entityData.define(SLIDING, false);
    }

    public boolean hasEgg() { return this.entityData.get(HAS_EGG); }
    public void setHasEgg(boolean hasEgg) { this.entityData.set(HAS_EGG, hasEgg); }
    public int getEggTicks() { return this.entityData.get(EGG_TICKS); }
    public void setEggTicks(int eggTicks) { this.entityData.set(EGG_TICKS, eggTicks); }
    public PenguinMood getMood() { return PenguinMood.MOODS[this.entityData.get(MOOD)]; }
    public void setMood(PenguinMood mood) { this.entityData.set(MOOD, mood.getId()); }
    public boolean isSliding() { return this.entityData.get(SLIDING); }
    public void setSliding(boolean sliding) { this.entityData.set(SLIDING, sliding); }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("HasEgg", this.hasEgg());
        nbt.putInt("EggTicks", this.getEggTicks());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setHasEgg(nbt.getBoolean("HasEgg"));
        this.setEggTicks(nbt.getInt("EggTicks"));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (this.hasEgg() && this.random.nextInt(4) == 0) return FrozenUpSoundEvents.ENTITY_PENGUIN_EGG_CRACK.get();
        else return FrozenUpSoundEvents.ENTITY_PENGUIN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.FISH_SWIM;
    }
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) { return FrozenUpSoundEvents.ENTITY_PENGUIN_HURT.get(); }
    @Nullable
    @Override
    protected SoundEvent getDeathSound() { return FrozenUpSoundEvents.ENTITY_PENGUIN_DEATH.get(); }
    @Override
    protected float getSoundVolume() { return 0.6F; }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        return new PenguinSwimNavigation(this, pLevel);
    }

    static class PenguinSwimNavigation extends WaterBoundPathNavigation {

        PenguinSwimNavigation(PenguinEntity penguin, Level world) { super(penguin, world); }

        @Override
        protected PathFinder createPathFinder(int range) {
            this.nodeEvaluator = new AmphibiousNodeEvaluator(false);
            return new PathFinder(this.nodeEvaluator, range);
        }

        @Override
        public boolean isStableDestination(BlockPos pPos) {
            return !this.level.getBlockState(pPos.below(2)).isAir();
        }

        @Override
        protected boolean canUpdatePath() {
            return true;
        }

    }

    static class PenguinMoveControl extends MoveControl {
        private final PenguinEntity penguin;

        public PenguinMoveControl(PenguinEntity penguin) {
            super(penguin);
            this.penguin = penguin;
        }

        @Override
        public void tick() {
            if (this.operation == Operation.STRAFE || this.operation == Operation.JUMPING || this.mob.onGround()) { super.tick();}
                if (this.operation == Operation.MOVE_TO && !this.penguin.getNavigation().isDone()) {
                    double d = this.wantedX - this.penguin.getX();
                    double e = this.wantedY - this.penguin.getY();
                    double f = this.wantedZ - this.penguin.getZ();
                    double g = d * d + e * e + f * f;
                    if (g < 2.5) {
                        this.mob.setZza(0.0F);
                    } else {
                        float movementSpeed = (float) (this.speedModifier * this.penguin.getAttributeValue(Attributes.MOVEMENT_SPEED));
                        this.penguin.setYRot(this.rotlerp(this.penguin.getYRot(), (float) (Mth.atan2(f, d) * 57.3) - 90.0F, 10.0F));
                        this.penguin.yBodyRot = this.penguin.getYRot();
                        this.penguin.yHeadRot = this.penguin.getYRot();
                        if (this.penguin.isInWater() && !this.penguin.hasEgg()) {
                            this.penguin.setSpeed(movementSpeed * 0.4F);
                            float j = -((float) (Mth.atan2(e, Mth.sqrt((float) (d * d + f * f))) * 57.3));
                            this.penguin.setXRot(this.rotlerp(this.penguin.getXRot(), Mth.clamp(Mth.wrapDegrees(j), -85.0F, 85.0F), 5.0F));
                            this.penguin.zza = Mth.cos(this.penguin.getXRot() * (float) Math.PI / 180f) * movementSpeed;
                            this.penguin.yya = -Mth.sin(this.penguin.getXRot() * (float) Math.PI / 180f);
                        }
                    }
                }
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob p_146744_) {
        return FrozenUpEntities.PENGUIN.get().create(world);
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType <PenguinEntity> entity, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random){
        return world.getBlockState(pos.below()).is(FrozenUpBlockTags.PENGUIN_SPAWNABLE_ON);
    }

}
