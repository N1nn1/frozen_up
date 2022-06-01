package net.teamdraco.frozenup.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.teamdraco.frozenup.entity.ai.goal.DigInGrassGoal;
import net.teamdraco.frozenup.init.FrozenUpEntities;
import net.teamdraco.frozenup.init.FrozenUpItems;
import net.teamdraco.frozenup.init.FrozenUpSoundEvents;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class ChillooEntity extends TameableEntity {
    private static final TrackedData<Integer> BANDS_COLOR = DataTracker.registerData(ChillooEntity.class, TrackedDataHandlerRegistry.INTEGER);
    static final Predicate<ItemEntity> PICKABLE_DROP_FILTER = (item) -> !item.cannotPickup() && item.isAlive();
    private float headRollProgress;
    private float lastHeadRollProgress;
    private int eatingTime;
    private int digInGrassTimer;
    private DigInGrassGoal digInGrassGoal;
    //TODO:
    // shedding/shearing feathers
    // custom sounds for eating
    // maybe comment some of the code since I dont have a clear understanding of what stuff does at the top of my head
    // breeding and tempting item tags
    // make them afraid of foxes and make only artic foxes attack them
    // Candles on truffle cake break them

    public ChillooEntity(EntityType<? extends ChillooEntity> type, World world) {
        super(type, world);
        this.setCanPickUpLoot(true);
    }

    @Override
    protected void initGoals() {
        this.digInGrassGoal = new DigInGrassGoal(this);
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.2F));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F, false));
        this.goalSelector.add(4, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(5, new ChillooEntity.PickupItemGoal());
        this.goalSelector.add(6, this.digInGrassGoal);
        this.goalSelector.add(7, new WanderAroundGoal(this, 1.0));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(9, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createChillooAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D).add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5F;
    }

    public float getHeadRoll(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.lastHeadRollProgress, this.headRollProgress) * 0.11F * 3.1415927F;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient && this.isAlive() && this.canMoveVoluntarily()) {
            ++this.eatingTime;
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (this.canEat(itemStack)) {
                if (this.eatingTime > 600) {
                    ItemStack itemStack2 = itemStack.finishUsing(this.world, this);
                    if (!itemStack2.isEmpty()) {
                        this.equipStack(EquipmentSlot.MAINHAND, itemStack2);
                    }

                    this.eatingTime = 0;
                } else if (this.eatingTime > 560 && this.random.nextFloat() < 0.1F) {
                    this.playSound(this.getEatSound(itemStack), 1.0F, 1.0F);
                    this.world.sendEntityStatus(this, (byte) 45);
                }
            }
        }
        if (this.world.isClient) {
            this.digInGrassTimer = Math.max(0, this.digInGrassTimer - 1);
        }
    }

    @Override
    protected void mobTick() {
        this.digInGrassTimer = this.digInGrassGoal.getTimer();
        super.mobTick();
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (this.world.isClient) {
            boolean bl = this.isOwner(player) || this.isTamed() || item == FrozenUpItems.TRUFFLE && !this.isTamed();
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
            } else if (item == FrozenUpItems.TRUFFLE) {
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
        EquipmentSlot equipmentSlot = EquipmentSlot.MAINHAND;
        if (!this.getEquippedStack(equipmentSlot).isEmpty()) {
            return false;
        } else {
            return super.canEquip(stack);
        }
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        Item item = stack.getItem();
        ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        return itemStack.isEmpty() || this.eatingTime > 0 && item.isFood() && !itemStack.getItem().isFood();
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 10) {
            this.digInGrassTimer = 40;
        } else {
            super.handleStatus(status);
        }
        if (status == 45) {
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (!itemStack.isEmpty()) {
                for(int i = 0; i < 8; ++i) {
                    Vec3d vec3d = (new Vec3d(((double)this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)).rotateX(-this.getPitch() * 0.0175F).rotateY(-this.getYaw() * 0.0175F);
                    this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack), this.getX() + this.getRotationVector().x / 2.0, this.getY(), this.getZ() + this.getRotationVector().z / 2.0, vec3d.x, vec3d.y + 0.05, vec3d.z);
                }
            }
        } else {
            super.handleStatus(status);
        }

    }

    public float getNeckAngle(float delta) {
        if (this.digInGrassTimer <= 0) {
            return 0.0F;
        } else if (this.digInGrassTimer >= 4 && this.digInGrassTimer <= 36) {
            return 1.0F;
        } else {
            return this.digInGrassTimer < 4 ? ((float)this.digInGrassTimer - delta) / 4.0F : -((float)(this.digInGrassTimer - 40) - delta) / 4.0F;
        }
    }

    public float getHeadAngle(float delta) {
        if (this.digInGrassTimer > 4 && this.digInGrassTimer <= 36) {
            float f = ((float)(this.digInGrassTimer - 4) - delta) / 32.0F;
            return 0.62831855F + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return this.digInGrassTimer > 0 ? 0.62831855F : this.getPitch() * 0.017453292F;
        }
    }

    private boolean canEat(ItemStack stack) {
        return stack.getItem().isFood() && this.onGround;
    }

    private void spit(ItemStack stack) {
        if (!stack.isEmpty() && !this.world.isClient) {
            ItemEntity itemEntity = new ItemEntity(this.world, this.getX() + this.getRotationVector().x, this.getY() + 1.0, this.getZ() + this.getRotationVector().z, stack);
            itemEntity.setPickupDelay(40);
            itemEntity.setThrower(this.getUuid());
            this.playSound(FrozenUpSoundEvents.ENTITY_CHILLOO_SPIT, 1.0F, 1.0F);
            this.world.spawnEntity(itemEntity);
        }
    }

    private void dropItem(ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), stack);
        this.world.spawnEntity(itemEntity);
    }

    @Override
    protected void loot(ItemEntity item) {
        ItemStack itemStack = item.getStack();
        if (this.canPickupItem(itemStack)) {
            int i = itemStack.getCount();
            if (i > 1) {
                this.dropItem(itemStack.split(i - 1));
            }

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
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putByte("BandsColor", (byte) this.getBandsColor().getId());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        if (tag.contains("BandsColor", 99)) {
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
    public boolean canBeLeashedBy (PlayerEntity player){
        return super.canBeLeashedBy(player);
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

    class PickupItemGoal extends Goal {
        public PickupItemGoal() {
            this.setControls(EnumSet.of(Control.MOVE));
        }

        @Override
        public boolean canStart() {
            if (!ChillooEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
                return false;
            } else if (ChillooEntity.this.getTarget() == null && ChillooEntity.this.getAttacker() == null) {
                if (!ChillooEntity.this.isNavigating()) {
                    return false;
                } else if (ChillooEntity.this.getRandom().nextInt(toGoalTicks(10)) != 0) {
                    return false;
                } else {
                    List<ItemEntity> list = ChillooEntity.this.world.getEntitiesByClass(ItemEntity.class, ChillooEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), ChillooEntity.PICKABLE_DROP_FILTER);
                    return !list.isEmpty() && ChillooEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
                }
            } else {
                return false;
            }
        }

        @Override
        public void tick() {
            List<ItemEntity> list = ChillooEntity.this.world.getEntitiesByClass(ItemEntity.class, ChillooEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), ChillooEntity.PICKABLE_DROP_FILTER);
            ItemStack itemStack = ChillooEntity.this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (itemStack.isEmpty() && !list.isEmpty()) {
                ChillooEntity.this.getNavigation().startMovingTo(list.get(0), 1.15);
            }

        }

        @Override
        public void start() {
            List<ItemEntity> list = ChillooEntity.this.world.getEntitiesByClass(ItemEntity.class, ChillooEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), ChillooEntity.PICKABLE_DROP_FILTER);
            if (!list.isEmpty()) {
                ChillooEntity.this.getNavigation().startMovingTo(list.get(0), 1.15);
            }

        }
    }
}


