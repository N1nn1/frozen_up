package teamdraco.frozenup.entity;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import teamdraco.frozenup.entity.ai.DiggingGoal;
import teamdraco.frozenup.init.FrozenUpEntities;
import teamdraco.frozenup.init.FrozenUpItems;
import teamdraco.frozenup.init.FrozenUpSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
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
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class ChillooEntity extends TameableEntity {
    public static final int DIG_ANIMATION_ID = 10;
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.ofItems(Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.COCOA_BEANS, Items.POTATO, Items.CARROT);
    private static final BiMap<Block, DyeColor> WOOL_BLOCKS = HashBiMap.create(16);
    private static final TrackedData<Byte> COLOR = DataTracker.registerData(ChillooEntity.class, TrackedDataHandlerRegistry.BYTE);
    public int timeUntilNextFeather = this.random.nextInt(10000) + 2500;
    public int digTimer = 0;

    static {
        WOOL_BLOCKS.put(Blocks.WHITE_WOOL, DyeColor.WHITE);
        WOOL_BLOCKS.put(Blocks.ORANGE_WOOL, DyeColor.ORANGE);
        WOOL_BLOCKS.put(Blocks.MAGENTA_WOOL, DyeColor.MAGENTA);
        WOOL_BLOCKS.put(Blocks.LIGHT_BLUE_WOOL, DyeColor.LIGHT_BLUE);
        WOOL_BLOCKS.put(Blocks.YELLOW_WOOL, DyeColor.YELLOW);
        WOOL_BLOCKS.put(Blocks.LIME_WOOL, DyeColor.LIME);
        WOOL_BLOCKS.put(Blocks.PINK_WOOL, DyeColor.PINK);
        WOOL_BLOCKS.put(Blocks.GRAY_WOOL, DyeColor.GRAY);
        WOOL_BLOCKS.put(Blocks.LIGHT_GRAY_WOOL, DyeColor.LIGHT_GRAY);
        WOOL_BLOCKS.put(Blocks.CYAN_WOOL, DyeColor.CYAN);
        WOOL_BLOCKS.put(Blocks.PURPLE_WOOL, DyeColor.PURPLE);
        WOOL_BLOCKS.put(Blocks.BLUE_WOOL, DyeColor.BLUE);
        WOOL_BLOCKS.put(Blocks.BROWN_WOOL, DyeColor.BROWN);
        WOOL_BLOCKS.put(Blocks.GREEN_WOOL, DyeColor.GREEN);
        WOOL_BLOCKS.put(Blocks.RED_WOOL, DyeColor.RED);
        WOOL_BLOCKS.put(Blocks.BLACK_WOOL, DyeColor.BLACK);
    }

    public ChillooEntity(EntityType<? extends ChillooEntity> type, World world) {
        super(type, world);
        this.stepHeight = 1;
        this.setTamed(false);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new EscapeDangerGoal(this, 1.4D));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.add(4, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(5, new TemptGoal(this, 1.0D, false, TEMPTATION_ITEMS));
        this.goalSelector.add(6, new DiggingGoal(this));
        this.goalSelector.add(7, new FollowParentGoal(this, 1.1D) {
            @Override
            public boolean canStart() {
                return !isTamed() && super.canStart();
            }
        });
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
    }

    protected float getActiveEyeHeight(EntityPose poseIn, EntityDimensions sizeIn) {
        return this.isBaby() ? 0.5F : 0.7F;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2F);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.WHEAT_SEEDS || stack.getItem() == Items.BEETROOT_SEEDS || stack.getItem() == Items.MELON_SEEDS || stack.getItem() == Items.COCOA_BEANS || stack.getItem() == Items.POTATO || stack.getItem() == Items.CARROT;
    }

    public void setBandColor(DyeColor color) {
        dataTracker.set(COLOR, color == null ? -1 : (byte) color.ordinal());
    }

    public DyeColor getBandColor() {
        byte color = dataTracker.get(COLOR);
        return color == -1 || color >= 16? null : DyeColor.byId(color);
    }

    public void setSweaterColor(DyeColor color) {
        dataTracker.set(COLOR, color == null ? -1 : (byte) (color.ordinal() + 16));
    }

    public DyeColor getSweaterColor() {
        byte color = dataTracker.get(COLOR);
        return color < 16 ? null : DyeColor.byId(color - 16);
    }

    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.setBandColor(DyeColor.RED);
            Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(12.0D);
        }
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        Item item = stack.getItem();
        if (item == FrozenUpItems.FROZEN_TRUFFLE && !this.isTamed()) {
            if (!player.abilities.creativeMode) {
                stack.decrement(1);
            }
            if (this.random.nextInt(3) == 0) {
                this.setOwner(player);
                this.navigation.stop();
                this.setSitting(true);
                this.world.sendEntityStatus(this, (byte) 7);
            } else {
                this.world.sendEntityStatus(this, (byte) 6);
            }
            return ActionResult.SUCCESS;
        }

        if (this.isOwner(player)) {
            if (item instanceof DyeItem) {
                setBandColor(((DyeItem) item).getColor());
                stack.decrement(1);
            } else {
                DyeColor dyeColor = WOOL_BLOCKS.get(Block.getBlockFromItem(item));
                if (dyeColor == null) {
                    if (item == Items.SHEARS) {
                        boolean failed = false;
                        DyeColor bandColor = getBandColor();
                        if (bandColor == null) {
                            DyeColor sweaterColor = getSweaterColor();
                            if (sweaterColor == null) {
                                failed = true;
                            } else {
                                dropItem(WOOL_BLOCKS.inverse().get(sweaterColor));
                            }
                        }
                        if (!failed) {
                            setBandColor(DyeColor.RED);
                            this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);
                            stack.damage(1, player, playerEntity -> playerEntity.sendToolBreakStatus(hand));
                        }
                    } else {
                        setSitting(!isInSittingPose());
                        this.jumping = false;
                        this.navigation.stop();
                        stack.decrement(1);
                    }
                } else {
                    setSweaterColor(dyeColor);
                    stack.decrement(1);
                }
            }
            return ActionResult.SUCCESS;
        }

        return super.interactMob(player, hand);
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
        } else if (digTimer > 0) --digTimer;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity ageable) {
        ChillooEntity chilloo = FrozenUpEntities.CHILLOO.create(this.world);
        UUID uuid = this.getOwnerUuid();
        if (uuid != null) {
            assert chilloo != null;
            chilloo.setOwnerUuid(uuid);
            chilloo.setTamed(true);
        }
        return chilloo;
    }

    protected SoundEvent getAmbientSound() {
        return FrozenUpSounds.CHILLOO_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FrozenUpSounds.CHILLOO_HURT;
    }

    protected SoundEvent getDeathSound() {
        return FrozenUpSounds.CHILLOO_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COLOR, (byte) 0);
    }

    public void readCustomDataFromTag(CompoundTag compound) {
        super.readCustomDataFromTag(compound);
        this.dataTracker.set(COLOR, compound.getByte("Colors"));
        if (compound.contains("FeatherLayTime")) {
            this.timeUntilNextFeather = compound.getInt("FeatherLayTime");
        }
    }

    public void writeCustomDataToTag(CompoundTag compound) {
        super.writeCustomDataToTag(compound);
        compound.putByte("Colors", this.dataTracker.get(COLOR));
        compound.putInt("FeatherLayTime", this.timeUntilNextFeather);
    }

    @Override
    public void handleStatus(byte id) {
        if (id == DIG_ANIMATION_ID) this.digTimer = 40;
        else super.handleStatus(id);
    }
}