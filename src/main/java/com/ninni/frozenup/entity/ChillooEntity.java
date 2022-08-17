package com.ninni.frozenup.entity;

import com.ninni.frozenup.FrozenUpTags;
import com.ninni.frozenup.criterion.FrozenUpCriteria;
import com.ninni.frozenup.entity.ai.goal.DigInGrassGoal;
import com.ninni.frozenup.item.FrozenUpItems;
import com.ninni.frozenup.sound.FrozenUpSoundEvents;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class ChillooEntity extends TameableEntity implements Shearable {
    private static final TrackedData<Integer> BANDS_COLOR = DataTracker.registerData(ChillooEntity.class, TrackedDataHandlerRegistry.INTEGER);
    static final Predicate<ItemEntity> PICKABLE_DROP_FILTER = (item) -> !item.cannotPickup() && item.isAlive();
    private static final TrackedData<Boolean> SHEARED = DataTracker.registerData(ChillooEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final String TIME_SINCE_SHEARED_KEY = "TimeSinceSheared";
    private float headRollProgress;
    private float lastHeadRollProgress;
    private int eatingTime;
    private int digInGrassTimer;
    private long timeSinceSheared;
    private DigInGrassGoal digInGrassGoal;

    public ChillooEntity(EntityType<? extends ChillooEntity> type, World world) {
        super(type, world);
        this.setCanPickUpLoot(true);
        this.stepHeight = 1;
    }

    @Override
    protected void initGoals() {
        this.digInGrassGoal = new DigInGrassGoal(this);
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, FoxEntity.class, 6.0F, 1, 1.2));
        this.goalSelector.add(4, new AnimalMateGoal(this, 1));
        this.goalSelector.add(4, new TemptGoal(this, 1.2, Ingredient.fromTag(FrozenUpTags.CHILLOO_BREED_ITEMS), false));
        this.goalSelector.add(6, new FollowOwnerGoal(this, 0.6, 10.0F, 2.0F, false));
        this.goalSelector.add(7, new ChillooEntity.PickupItemGoal());
        this.goalSelector.add(8, this.digInGrassGoal);
        this.goalSelector.add(9, new WanderAroundFarGoal(this, 0.6));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(11, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createChillooAttributes() {
        return MobEntity.createMobAttributes()
                        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                        .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0);
    }

    @Override protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) { return dimensions.height * 0.5F; }
    public float getHeadRoll(float tickDelta) { return MathHelper.lerp(tickDelta, this.lastHeadRollProgress, this.headRollProgress) * 0.11F * (float)Math.PI; }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient && this.isAlive() && this.canMoveVoluntarily()) {
            ++this.eatingTime;
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (this.canEat(itemStack)) {
                if (this.eatingTime > 600) {
                    ItemStack itemStack2 = itemStack.finishUsing(this.world, this);
                    if (!itemStack2.isEmpty()) this.equipStack(EquipmentSlot.MAINHAND, itemStack2);
                    this.eatingTime = 0;
                } else if (this.eatingTime > 560 && this.random.nextFloat() < 0.1F) {
                    this.playSound(this.getEatSound(itemStack), 1.0F, 2.0F);
                    this.world.sendEntityStatus(this, (byte) 45);
                }
            }
        }
        if (this.world.isClient) this.digInGrassTimer = Math.max(0, this.digInGrassTimer - 1);
    }

    @Override
    protected void mobTick() {
        this.digInGrassTimer = this.digInGrassGoal.getTimer();
        super.mobTick();

        long time = this.world.getTime();
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
            if (this.getWorld().getBiome(this.getBlockPos()).isIn(ConventionalBiomeTags.CLIMATE_HOT)) {
                for (int i = 0; i < 0.25; ++i) {
                    double velX = this.random.nextGaussian() * -5;
                    double velY = this.random.nextGaussian() * -5;
                    double velZ = this.random.nextGaussian() * -5;
                    this.world.addParticle(ParticleTypes.FALLING_WATER, this.getParticleX(0.4), this.getBodyY(1) - 0.5, this.getParticleZ(0.4), velX, velY, velZ);
                }
            }
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();

        if (itemStack.isIn(ConventionalItemTags.SHEARS)) {
            if (!this.world.isClient && this.isShearable()) {
                this.sheared(SoundCategory.PLAYERS);
                this.emitGameEvent(GameEvent.SHEAR, player);
                itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
                return ActionResult.SUCCESS;
            }
            return ActionResult.CONSUME;
        }

        if (item == FrozenUpItems.TRUFFLE && this.getHealth() < this.getMaxHealth() && this.isTamed()) feedTruffleToChilloo(player);
        if (item == FrozenUpItems.TRUFFLE && !this.isTamed()) feedTruffleToChilloo(player);

        if (this.world.isClient) return this.isOwner(player) || this.isTamed() || item == FrozenUpItems.TRUFFLE && !this.isTamed() ? ActionResult.CONSUME : ActionResult.PASS;
        else {

            if (this.isTamed()) {
                ItemStack stackInHand = this.getEquippedStack(EquipmentSlot.MAINHAND);
                if (!stackInHand.isEmpty() && itemStack.isEmpty() && !player.shouldCancelInteraction()) {
                    player.giveItemStack(stackInHand);
                    stackInHand.decrement(1);
                    if (player instanceof ServerPlayerEntity) FrozenUpCriteria.RETRIEVE_ITEM_FROM_TAMED_CHILLOO.trigger((ServerPlayerEntity) player);
                    if (!this.isSilent()) this.world.playSoundFromEntity(null, this, FrozenUpSoundEvents.ENTITY_CHILLOO_SPIT, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                    return ActionResult.SUCCESS;
                }

                if (item == FrozenUpItems.TRUFFLE && this.getHealth() < this.getMaxHealth()) {
                    this.heal(Objects.requireNonNull(item.getFoodComponent()).getHunger());
                    return ActionResult.SUCCESS;
                }

                if (!itemStack.isIn(ConventionalItemTags.DYES) &&this.isOwner(player)) {
                    this.setSitting(!this.isSitting());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                    return ActionResult.SUCCESS;
                }


                DyeColor dyeColor = ((DyeItem)item).getColor();
                if (dyeColor != this.getBandsColor()) {
                    this.setBandsColor(dyeColor);
                    if (!player.getAbilities().creativeMode) itemStack.decrement(1);
                    return ActionResult.SUCCESS;
                }
            } else if (item == FrozenUpItems.TRUFFLE) {
                this.setOwner(player);
                this.navigation.stop();
                this.setTarget(null);
                this.setSitting(true);
                this.world.sendEntityStatus(this, (byte)7);
                if (player instanceof ServerPlayerEntity) FrozenUpCriteria.TAME_A_CHILLOO.trigger((ServerPlayerEntity) player);
                return ActionResult.SUCCESS;
            }

            return super.interactMob(player, hand);
        }
    }

    public void feedTruffleToChilloo(PlayerEntity player) {
        Vec3d vec3d = this.getBoundingBox().getCenter();
        Random random = this.world.getRandom();
        ItemStack stack = FrozenUpItems.TRUFFLE.getDefaultStack();
        if (!this.isSilent()) this.world.playSoundFromEntity(null, this, FrozenUpSoundEvents.ENTITY_CHILLOO_EAT, this.getSoundCategory(), 1.5F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        if (!player.getAbilities().creativeMode) player.getStackInHand(player.getActiveHand()).decrement(1);
        for (int i = 0; i < 10; ++i) {
            double velX = random.nextGaussian() * 0.075D;
            double velY = random.nextGaussian() * 0.075D;
            double velZ = random.nextGaussian() * 0.075D;
            this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), vec3d.x, vec3d.y, vec3d.z, velX, velY, velZ);
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(FrozenUpTags.CHILLOO_BREED_ITEMS);
    }

    @Override
    protected void drop(DamageSource source) {
        ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        if (!itemStack.isEmpty()) {
            this.dropStack(itemStack);
            this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

        super.drop(source);
    }

    @Override
    public boolean canEquip(ItemStack stack) {
        if (!this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) return false;
        else return super.canEquip(stack);
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        if (stack.isOf(FrozenUpItems.CHILLOO_FEATHER)) return false;
        return itemStack.isEmpty() || this.eatingTime > 0 && stack.getItem().isFood() && !itemStack.getItem().isFood();
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 10) this.digInGrassTimer = 40;
        else super.handleStatus(status);
        if (status == 45) {
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (!itemStack.isEmpty()) {
                for(int i = 0; i < 8; ++i) {
                    Vec3d vec3d = (new Vec3d(((double)this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)).rotateX(-this.getPitch() * 0.0175F).rotateY(-this.getYaw() * 0.0175F);
                    this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack), this.getX() + this.getRotationVector().x / 2.0, this.getY(), this.getZ() + this.getRotationVector().z / 2.0, vec3d.x, vec3d.y + 0.05, vec3d.z);
                }
            }
        } else super.handleStatus(status);
    }

    public float getNeckAngle(float delta) {
        if (this.digInGrassTimer <= 0) return 0.0F;
        else if (this.digInGrassTimer >= 4 && this.digInGrassTimer <= 36) return 1.0F;
        else return this.digInGrassTimer < 4 ? ((float)this.digInGrassTimer - delta) / 4.0F : -((float)(this.digInGrassTimer - 40) - delta) / 4.0F;
    }

    public float getHeadAngle(float delta) {
        if (this.digInGrassTimer > 4 && this.digInGrassTimer <= 36) {
            float f = ((float)(this.digInGrassTimer - 4) - delta) / 32.0F;
            return 0.62831855F + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else return this.digInGrassTimer > 0 ? 0.62831855F : this.getPitch() * 0.017453292F;
    }

    private boolean canEat(ItemStack stack) { return stack.getItem().isFood() && this.onGround; }

    private void spit(ItemStack stack) {
        if (!stack.isEmpty() && !this.world.isClient) {
            ItemEntity itemEntity = new ItemEntity(this.world, this.getX() + this.getRotationVector().x, this.getY() + 1.0, this.getZ() + this.getRotationVector().z, stack);
            itemEntity.setPickupDelay(40);
            itemEntity.setThrower(this.getUuid());
            this.playSound(FrozenUpSoundEvents.ENTITY_CHILLOO_SPIT, 1.0F, 1.0F);
            this.world.spawnEntity(itemEntity);
        }
    }

    private void dropItem(ItemStack stack) { this.world.spawnEntity(new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), stack)); }

    @Override
    protected void loot(ItemEntity item) {
        ItemStack itemStack = item.getStack();
        if (this.canPickupItem(itemStack)) {
            int i = itemStack.getCount();
            if (i > 1) this.dropItem(itemStack.split(i - 1));
            this.spit(this.getEquippedStack(EquipmentSlot.MAINHAND));
            this.triggerItemPickedUpByEntityCriteria(item);
            this.equipStack(EquipmentSlot.MAINHAND, itemStack.split(1));
            this.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 2.0F;
            this.sendPickup(item, itemStack.getCount());
            item.discard();
            this.eatingTime = 0;
        }

    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BANDS_COLOR, DyeColor.RED.getId());
        this.dataTracker.startTracking(SHEARED, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putBoolean("Sheared", this.isSheared());
        tag.putLong(TIME_SINCE_SHEARED_KEY, this.timeSinceSheared);
        tag.putByte("BandsColor", (byte) this.getBandsColor().getId());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.setSheared(tag.getBoolean("Sheared"));
        this.timeSinceSheared = tag.getLong(TIME_SINCE_SHEARED_KEY);
        if (tag.contains("BandsColor", 99)) this.setBandsColor(DyeColor.byId(tag.getInt("BandsColor")));
    }

    public boolean isSheared() { return this.dataTracker.get(SHEARED).equals(true); }

    public void setSheared(boolean sheared) { this.dataTracker.set(SHEARED, sheared); }

    @Override public boolean isShearable() { return this.isAlive() && !this.isSheared() && !this.isBaby(); }

    public DyeColor getBandsColor() { return DyeColor.byId(this.dataTracker.get(BANDS_COLOR)); }

    public void setBandsColor (DyeColor color) { this.dataTracker.set(BANDS_COLOR, color.getId()); }

    @Override public boolean canBeLeashedBy (PlayerEntity player) { return super.canBeLeashedBy(player); }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(this.getMaxHealth());
        } else this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(12.0D);
    }

    @Nullable
    @Override
    public PassiveEntity createChild (ServerWorld world, PassiveEntity entity) {
        ChillooEntity chilloo = FrozenUpEntities.CHILLOO.create(world);
        UUID uUID = this.getOwnerUuid();
        if (uUID != null && chilloo != null) {
            chilloo.setOwnerUuid(uUID);
            chilloo.setTamed(true);
        }
        return chilloo;
    }

    public void onDiggingInGrass() {}

    @Override protected SoundEvent getAmbientSound() { return FrozenUpSoundEvents.ENTITY_CHILLOO_AMBIENT; }
    @Override protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return FrozenUpSoundEvents.ENTITY_CHILLOO_HURT; }
    @Override protected SoundEvent getDeathSound() { return FrozenUpSoundEvents.ENTITY_CHILLOO_DEATH; }
    @Override protected void playStepSound(BlockPos pos, BlockState blockIn) { this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F); }

    @Override
    public void sheared(SoundCategory category) {
        this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, category, 1.0f, 1.0f);
        this.setSheared(true);
        if (this.isSheared()) this.timeSinceSheared = this.world.getTime();
        for (int i = 0, l = 2 + this.random.nextInt(5); i < l; i++) {
            ItemEntity itemEntity = this.dropItem(FrozenUpItems.CHILLOO_FEATHER, 1);
            if (itemEntity == null) continue;
            itemEntity.setVelocity(itemEntity.getVelocity().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1f, this.random.nextFloat() * 0.05f, (this.random.nextFloat() - this.random.nextFloat()) * 0.1f));
        }
    }


    class PickupItemGoal extends Goal {
        public PickupItemGoal() { this.setControls(EnumSet.of(Control.MOVE)); }

        @Override
        public boolean canStart() {
            if (!ChillooEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) return false;
            else if (ChillooEntity.this.getTarget() == null && ChillooEntity.this.getAttacker() == null) {
                if (!ChillooEntity.this.isNavigating()) return false;
                else if (ChillooEntity.this.getRandom().nextInt(toGoalTicks(10)) != 0) return false;
                else return !ChillooEntity.this.world.getEntitiesByClass(ItemEntity.class, ChillooEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), ChillooEntity.PICKABLE_DROP_FILTER).isEmpty() && ChillooEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
            } else return false;
        }

        @Override
        public void tick() {
            List<ItemEntity> list = ChillooEntity.this.world.getEntitiesByClass(ItemEntity.class, ChillooEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), ChillooEntity.PICKABLE_DROP_FILTER);
            if (ChillooEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty() && !list.isEmpty()) ChillooEntity.this.getNavigation().startMovingTo(list.get(0), 1);
        }

        @Override
        public void start() {
            List<ItemEntity> list = ChillooEntity.this.world.getEntitiesByClass(ItemEntity.class, ChillooEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), ChillooEntity.PICKABLE_DROP_FILTER);
            if (!list.isEmpty()) ChillooEntity.this.getNavigation().startMovingTo(list.get(0), 1);
        }
    }
}


