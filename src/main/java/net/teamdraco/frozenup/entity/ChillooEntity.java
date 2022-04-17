package net.teamdraco.frozenup.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.teamdraco.frozenup.entity.ai.goal.DigInGrassGoal;
import net.teamdraco.frozenup.init.FrozenUpEntities;
import net.teamdraco.frozenup.init.FrozenUpItems;
import net.teamdraco.frozenup.init.FrozenUpSoundEvents;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ChillooEntity extends TameableEntity {
    public static final int DIG_ANIMATION_ID = 10;
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.ofItems(Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.COCOA_BEANS, Items.POTATO, Items.CARROT, Items.BEETROOT, Items.PUMPKIN_SEEDS);
    public int timeUntilNextFeather = this.random.nextInt(10000) + 2500;
    public int digTimer = 0;
    private static final TrackedData<Integer> BANDS_COLOR = DataTracker.registerData(ChillooEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public ChillooEntity(EntityType<? extends ChillooEntity> type, World world) {
        super(type, world);
        this.stepHeight = 1;
        this.setTamed(false);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new FollowOwnerGoal(this, 1.2D, 10.0F, 2.0F, false));
        this.goalSelector.add(4, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(5, new TemptGoal(this, 1.0D, TEMPTATION_ITEMS, false));
        this.goalSelector.add(6, new DigInGrassGoal(this));
        this.goalSelector.add(7, new FollowParentGoal(this, 1.1D) {
            @Override
            public boolean canStart() {
                return !isTamed() && super.canStart();
            }
        });
        this.goalSelector.add(8, new EscapeDangerGoal(this, 1.4D));
        this.goalSelector.add(9, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(10, new WanderAroundGoal(this, 1.0D));
        this.goalSelector.add(11, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BANDS_COLOR, DyeColor.RED.getId());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putByte("BandsColor", (byte) this.getBandsColor().getId());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        if (tag.contains("BandanaColor", 99)) {
            this.setBandsColor(DyeColor.byId(tag.getInt("BandsColor")));
        }
    }

    public DyeColor getBandsColor () {
        return DyeColor.byId(this.dataTracker.get(BANDS_COLOR));
    }

    public void setBandsColor (DyeColor color){
        this.dataTracker.set(BANDS_COLOR, color.getId());
    }

    @Override
    protected float getActiveEyeHeight(EntityPose poseIn, EntityDimensions sizeIn) {
        return this.isBaby() ? 0.5F : 0.7F;
    }

    @Override
    public boolean canBeLeashedBy (PlayerEntity player){
        return super.canBeLeashedBy(player);
    }

    public static DefaultAttributeContainer.Builder createChillooAttributes() {
        return MobEntity.createMobAttributes()
                        .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
                        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.WHEAT_SEEDS || item == Items.BEETROOT_SEEDS || item == Items.MELON_SEEDS || item == Items.COCOA_BEANS || item == Items.POTATO || item == Items.CARROT|| item == Items.BEETROOT || item == Items.PUMPKIN_SEEDS;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(this.getMaxHealth());
        } else {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(12.0D);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (this.world.isClient) {
            boolean bl = this.isOwner(player) || this.isTamed() || item == FrozenUpItems.FROZEN_TRUFFLE && !this.isTamed();
            return bl ? ActionResult.CONSUME : ActionResult.PASS;
        } else {
            if (this.isTamed()) {
                if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                    if (!this.isSilent()) {
                        this.world.playSoundFromEntity(null, this, FrozenUpSoundEvents.ENTITY_CHILLOO_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                    }
                    if (!player.getAbilities().creativeMode) {
                        itemStack.decrement(1);
                    }

                    this.heal(1.0F);
                    return ActionResult.SUCCESS;
                }

                if (!(item instanceof DyeItem)) {
                    ActionResult actionResult = super.interactMob(player, hand);
                    if ((!actionResult.isAccepted() || this.isBaby()) && this.isOwner(player)) {
                        this.setSitting(!this.isSitting());
                        this.jumping = false;
                        this.navigation.stop();
                        this.setTarget(null);
                        return ActionResult.SUCCESS;
                    }

                    return actionResult;
                }

                DyeColor dyeColor = ((DyeItem)item).getColor();
                if (dyeColor != this.getBandsColor()) {
                    this.setBandsColor(dyeColor);
                    if (!player.getAbilities().creativeMode) {
                        itemStack.decrement(1);
                    }

                    return ActionResult.SUCCESS;
                }
            } else if (item == FrozenUpItems.FROZEN_TRUFFLE) {
                if (!this.isSilent()) {
                    this.world.playSoundFromEntity(null, this, FrozenUpSoundEvents.ENTITY_CHILLOO_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                }
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                if (this.random.nextInt(3) == 0) {
                    this.setOwner(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setSitting(true);
                    this.world.sendEntityStatus(this, (byte)7);
                } else {
                    this.world.sendEntityStatus(this, (byte)6);
                }

                return ActionResult.SUCCESS;
            }

            return super.interactMob(player, hand);
        }
    }


    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient) {
            if (this.isAlive() && !this.isBaby() && --this.timeUntilNextFeather <= 0 && this.isTamed()) {
                this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.dropItem(FrozenUpItems.CHILLOO_FEATHER);
                this.timeUntilNextFeather = this.random.nextInt(10000) + 5000;
            }
        } else if (digTimer > 0) {
            digTimer--;
        }
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

    @Override
    protected SoundEvent getAmbientSound() {
        return FrozenUpSoundEvents.ENTITY_CHILLOO_AMBIENT;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FrozenUpSoundEvents.ENTITY_CHILLOO_HURT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return FrozenUpSoundEvents.ENTITY_CHILLOO_DEATH;
    }
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
    }


    @Override
    public NbtCompound writeNbt(NbtCompound compound) {
        compound.putInt("FeatherLayTime", this.timeUntilNextFeather);

        return super.writeNbt(compound);
    }
    @Override
    public void readNbt(NbtCompound compound) {
        super.readNbt(compound);
        if (compound.contains("FeatherLayTime")) {
            this.timeUntilNextFeather = compound.getInt("FeatherLayTime");
        }
    }

    @Override
    public void handleStatus(byte id) {
        if (id == DIG_ANIMATION_ID) {
            this.digTimer = 40;
        } else {
            super.handleStatus(id);
        }
    }
}