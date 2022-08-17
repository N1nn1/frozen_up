package com.ninni.frozenup.entity;

import com.ninni.frozenup.FrozenUpTags;
import com.ninni.frozenup.entity.ai.goal.DigInGrassGoal;
import com.ninni.frozenup.init.FrozenUpEntities;
import com.ninni.frozenup.init.FrozenUpItems;
import com.ninni.frozenup.init.FrozenUpSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class ChillooEntity extends TamableAnimal implements Shearable {
    private static final EntityDataAccessor<Integer> BANDS_COLOR = SynchedEntityData.defineId(ChillooEntity.class, EntityDataSerializers.INT);
    static final Predicate<ItemEntity> PICKABLE_DROP_FILTER = (item) -> !item.hasPickUpDelay() && item.isAlive();
    private static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(ChillooEntity.class, EntityDataSerializers.BOOLEAN);
    public static final String TIME_SINCE_SHEARED_KEY = "TimeSinceSheared";
    private float headRollProgress;
    private float lastHeadRollProgress;
    private int eatingTime;
    private int digInGrassTimer;
    private long timeSinceSheared;
    private DigInGrassGoal digInGrassGoal;

    public ChillooEntity(EntityType<? extends ChillooEntity> type, Level world) {
        super(type, world);
        this.setCanPickUpLoot(true);
    }

    @Override
    public float getStepHeight() {
        return 1;
    }

    @Override
    protected void registerGoals() {
        this.digInGrassGoal = new DigInGrassGoal(this);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Fox.class, 6.0F, 1, 1.2));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, Ingredient.of(FrozenUpTags.CHILLOO_BREED_ITEMS), false));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 0.6, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(7, new ChillooEntity.PickupItemGoal());
        this.goalSelector.addGoal(8, this.digInGrassGoal);
        this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createChillooAttributes() {
        return Mob.createMobAttributes()
                        .add(Attributes.MOVEMENT_SPEED, 0.25)
                        .add(Attributes.MAX_HEALTH, 12.0);
    }

    @Override
    public float getEyeHeightAccess(Pose pose, EntityDimensions size) {
        return size.height * 0.5F;
    }

    public float getHeadRoll(float tickDelta) { return Mth.lerp(tickDelta, this.lastHeadRollProgress, this.headRollProgress) * 0.11F * (float)Math.PI; }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide && this.isAlive() && this.isEffectiveAi()) {
            ++this.eatingTime;
            ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (this.canEat(itemStack)) {
                if (this.eatingTime > 600) {
                    ItemStack itemStack2 = itemStack.finishUsingItem(this.level, this);
                    if (!itemStack2.isEmpty()) this.setItemSlot(EquipmentSlot.MAINHAND, itemStack2);
                    this.eatingTime = 0;
                } else if (this.eatingTime > 560 && this.random.nextFloat() < 0.1F) {
                    this.playSound(this.getEatingSound(itemStack), 1.0F, 1.0F);
                    this.level.broadcastEntityEvent(this, (byte) 45);
                }
            }
        }
        if (this.level.isClientSide) this.digInGrassTimer = Math.max(0, this.digInGrassTimer - 1);
    }

    @Override
    protected void customServerAiStep() {
        this.digInGrassTimer = this.digInGrassGoal.getTimer();
        super.customServerAiStep();

        long time = this.level.getGameTime();
        if (this.isSheared()) {
            if (this.random.nextInt((int) (time - this.timeSinceSheared)) > 20 * 210) {
                this.timeSinceSheared = time;
                this.setSheared(false);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.isSheared()) {
            if (this.getLevel().getBiome(this.blockPosition()).is(Tags.Biomes.IS_HOT_OVERWORLD)) {
                for (int i = 0; i < 0.25; ++i) {
                    double velX = this.random.nextGaussian() * -5;
                    double velY = this.random.nextGaussian() * -5;
                    double velZ = this.random.nextGaussian() * -5;
                    this.level.addParticle(ParticleTypes.FALLING_WATER, this.getRandomX(0.4), this.getY(1) - 0.5, this.getRandomZ(0.4), velX, velY, velZ);
                }
            }
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (itemStack.is(Tags.Items.SHEARS)) {
            if (!this.level.isClientSide && this.readyForShearing()) {
                this.shear(SoundSource.PLAYERS);
                this.gameEvent(GameEvent.SHEAR, player);
                itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }

        if (item == FrozenUpItems.TRUFFLE.get() && this.getHealth() < this.getMaxHealth() && this.isTame()) feedTruffleToChilloo(player);
        if (item == FrozenUpItems.TRUFFLE.get() && !this.isTame()) feedTruffleToChilloo(player);

        if (this.level.isClientSide) return this.isOwnedBy(player) || this.isTame() || item == FrozenUpItems.TRUFFLE.get() && !this.isTame() ? InteractionResult.CONSUME : InteractionResult.PASS;
        else {
            if (this.isTame()) {
                ItemStack stackInHand = this.getItemBySlot(EquipmentSlot.MAINHAND);
                if (!stackInHand.isEmpty() && itemStack.isEmpty() && !player.isSecondaryUseActive()) {
                    player.addItem(stackInHand);
                    stackInHand.shrink(1);
                    if (!this.isSilent()) this.level.playSound(null, this, FrozenUpSoundEvents.ENTITY_CHILLOO_SPIT.get(), this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                    return InteractionResult.SUCCESS;
                }

                if (item == FrozenUpItems.TRUFFLE.get() && this.getHealth() < this.getMaxHealth()) {
                    this.heal(Objects.requireNonNull(item.getFoodProperties()).getNutrition());
                    return InteractionResult.SUCCESS;
                }

                if (!itemStack.is(Tags.Items.DYES) && this.isOwnedBy(player)) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                    return InteractionResult.SUCCESS;
                }

                DyeColor dyeColor = ((DyeItem)item).getDyeColor();
                if (dyeColor != this.getBandsColor()) {
                    this.setBandsColor(dyeColor);
                    if (!player.getAbilities().instabuild) itemStack.shrink(1);
                    return InteractionResult.SUCCESS;
                }
            } else if (item == FrozenUpItems.TRUFFLE.get()) {
                this.tame(player);
                this.navigation.stop();
                this.setTarget(null);
                this.setOrderedToSit(true);
                this.level.broadcastEntityEvent(this, (byte)7);
                return InteractionResult.SUCCESS;
            }

            return super.mobInteract(player, hand);
        }
    }

    public void feedTruffleToChilloo(Player player) {
        Vec3 vec3d = this.getBoundingBox().getCenter();
        RandomSource random = this.level.getRandom();
        ItemStack stack = FrozenUpItems.TRUFFLE.get().getDefaultInstance();
        if (!this.isSilent()) this.level.playSound(null, this, FrozenUpSoundEvents.ENTITY_CHILLOO_EAT.get(), this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        if (!player.getAbilities().instabuild) player.getItemInHand(player.getUsedItemHand()).shrink(1);
        for (int i = 0; i < 10; ++i) {
            double velX = random.nextGaussian() * 0.075D;
            double velY = random.nextGaussian() * 0.075D;
            double velZ = random.nextGaussian() * 0.075D;
            this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), vec3d.x, vec3d.y, vec3d.z, velX, velY, velZ);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(FrozenUpTags.CHILLOO_BREED_ITEMS);
    }

    @Override
    protected void dropAllDeathLoot(DamageSource source) {
        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!itemStack.isEmpty()) {
            this.spawnAtLocation(itemStack);
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

        super.dropAllDeathLoot(source);
    }

    @Override
    public boolean canTakeItem(ItemStack stack) {
        if (!this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) return false;
        else return super.canTakeItem(stack);
    }

    @Override
    public boolean canHoldItem(ItemStack stack) {
        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (stack.is(FrozenUpItems.CHILLOO_FEATHER.get())) return false;
        return itemStack.isEmpty() || this.eatingTime > 0 && stack.getItem().isEdible() && !itemStack.getItem().isEdible();
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == 10) this.digInGrassTimer = 40;
        else super.handleEntityEvent(status);
        if (status == 45) {
            ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (!itemStack.isEmpty()) {
                for(int i = 0; i < 8; ++i) {
                    Vec3 vec3d = (new Vec3(((double)this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)).xRot(-this.getXRot() * 0.0175F).yRot(-this.getYRot() * 0.0175F);
                    this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemStack), this.getX() + this.getLookAngle().x / 2.0, this.getY(), this.getZ() + this.getLookAngle().z / 2.0, vec3d.x, vec3d.y + 0.05, vec3d.z);
                }
            }
        } else super.handleEntityEvent(status);
    }

    public float getNeckAngle(float delta) {
        if (this.digInGrassTimer <= 0) return 0.0F;
        else if (this.digInGrassTimer >= 4 && this.digInGrassTimer <= 36) return 1.0F;
        else return this.digInGrassTimer < 4 ? ((float)this.digInGrassTimer - delta) / 4.0F : -((float)(this.digInGrassTimer - 40) - delta) / 4.0F;
    }

    public float getHeadAngle(float delta) {
        if (this.digInGrassTimer > 4 && this.digInGrassTimer <= 36) {
            float f = ((float)(this.digInGrassTimer - 4) - delta) / 32.0F;
            return 0.62831855F + 0.21991149F * Mth.sin(f * 28.7F);
        } else return this.digInGrassTimer > 0 ? 0.62831855F : this.getXRot() * 0.017453292F;
    }

    private boolean canEat(ItemStack stack) { return stack.getItem().isEdible() && this.onGround; }

    private void spit(ItemStack stack) {
        if (!stack.isEmpty() && !this.level.isClientSide) {
            ItemEntity itemEntity = new ItemEntity(this.level, this.getX() + this.getLookAngle().x, this.getY() + 1.0, this.getZ() + this.getLookAngle().z, stack);
            itemEntity.setPickUpDelay(40);
            itemEntity.setThrower(this.getUUID());
            this.playSound(FrozenUpSoundEvents.ENTITY_CHILLOO_SPIT.get(), 1.0F, 1.0F);
            this.level.addFreshEntity(itemEntity);
        }
    }

    private void dropItem(ItemStack stack) { this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), stack)); }

    @Override
    protected void pickUpItem(ItemEntity item) {
        ItemStack itemStack = item.getItem();
        if (this.canHoldItem(itemStack)) {
            int i = itemStack.getCount();
            if (i > 1) this.dropItem(itemStack.split(i - 1));
            this.spit(this.getItemBySlot(EquipmentSlot.MAINHAND));
            this.onItemPickup(item);
            this.setItemSlot(EquipmentSlot.MAINHAND, itemStack.split(1));
            this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0F;
            this.take(item, itemStack.getCount());
            item.discard();
            this.eatingTime = 0;
        }

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BANDS_COLOR, DyeColor.RED.getId());
        this.entityData.define(SHEARED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Sheared", this.isSheared());
        tag.putLong(TIME_SINCE_SHEARED_KEY, this.timeSinceSheared);
        tag.putByte("BandsColor", (byte) this.getBandsColor().getId());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSheared(tag.getBoolean("Sheared"));
        this.timeSinceSheared = tag.getLong(TIME_SINCE_SHEARED_KEY);
        if (tag.contains("BandsColor", 99)) this.setBandsColor(DyeColor.byId(tag.getInt("BandsColor")));
    }

    public boolean isSheared() { return this.entityData.get(SHEARED).equals(true); }

    public void setSheared(boolean sheared) { this.entityData.set(SHEARED, sheared); }

    public DyeColor getBandsColor() { return DyeColor.byId(this.entityData.get(BANDS_COLOR)); }

    public void setBandsColor (DyeColor color) { this.entityData.set(BANDS_COLOR, color.getId()); }

    @Override public boolean canBeLeashed(Player player) { return super.canBeLeashed(player); }

    @Override
    public void setTame(boolean tamed) {
        super.setTame(tamed);
        if (tamed) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(this.getMaxHealth());
        } else this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(12.0D);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob p_146744_) {
        ChillooEntity chilloo = FrozenUpEntities.CHILLOO.get().create(world);
        UUID uUID = this.getOwnerUUID();
        if (uUID != null && chilloo != null) {
            chilloo.setOwnerUUID(uUID);
            chilloo.setTame(true);
        }
        return chilloo;
    }

    public void onDiggingInGrass() {}

    @Override protected SoundEvent getAmbientSound() { return FrozenUpSoundEvents.ENTITY_CHILLOO_AMBIENT.get(); }
    @Override protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return FrozenUpSoundEvents.ENTITY_CHILLOO_HURT.get(); }
    @Override protected SoundEvent getDeathSound() { return FrozenUpSoundEvents.ENTITY_CHILLOO_DEATH.get(); }
    @Override protected void playStepSound(BlockPos pos, BlockState blockIn) { this.playSound(SoundEvents.SHEEP_STEP, 0.15F, 1.0F); }

    @Override
    public void shear(SoundSource category) {
        this.level.playSound(null, this, SoundEvents.SHEEP_SHEAR, category, 1.0f, 1.0f);
        this.setSheared(true);
        if (this.isSheared()) this.timeSinceSheared = this.level.getGameTime();
        for (int i = 0, l = 2 + this.random.nextInt(5); i < l; i++) {
            ItemEntity itemEntity = this.spawnAtLocation(FrozenUpItems.CHILLOO_FEATHER.get(), 1);
            if (itemEntity == null) continue;
            itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1f, this.random.nextFloat() * 0.05f, (this.random.nextFloat() - this.random.nextFloat()) * 0.1f));
        }
    }

    @Override
    public boolean readyForShearing() {
        return this.isAlive() && !this.isSheared() && !this.isBaby();
    }


    class PickupItemGoal extends Goal {
        public PickupItemGoal() { this.setFlags(EnumSet.of(Flag.MOVE)); }

        @Override
        public boolean canUse() {
            if (!ChillooEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) return false;
            else if (ChillooEntity.this.getTarget() == null && ChillooEntity.this.getLastHurtByMob() == null) {
                if (!ChillooEntity.this.isPathFinding()) return false;
                else if (ChillooEntity.this.getRandom().nextInt(reducedTickDelay(10)) != 0) return false;
                else return !ChillooEntity.this.level.getEntitiesOfClass(ItemEntity.class, ChillooEntity.this.getBoundingBox().inflate(8.0, 8.0, 8.0), ChillooEntity.PICKABLE_DROP_FILTER).isEmpty() && ChillooEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
            } else return false;
        }

        @Override
        public void tick() {
            List<ItemEntity> list = ChillooEntity.this.level.getEntitiesOfClass(ItemEntity.class, ChillooEntity.this.getBoundingBox().inflate(8.0, 8.0, 8.0), ChillooEntity.PICKABLE_DROP_FILTER);
            if (ChillooEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && !list.isEmpty()) ChillooEntity.this.getNavigation().createPath(list.get(0), 1);
        }

        @Override
        public void start() {
            List<ItemEntity> list = ChillooEntity.this.level.getEntitiesOfClass(ItemEntity.class, ChillooEntity.this.getBoundingBox().inflate(8.0, 8.0, 8.0), ChillooEntity.PICKABLE_DROP_FILTER);
            if (!list.isEmpty()) ChillooEntity.this.getNavigation().createPath(list.get(0), 1);
        }
    }
}


