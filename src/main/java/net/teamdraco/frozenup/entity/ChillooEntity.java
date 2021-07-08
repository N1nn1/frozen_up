package net.teamdraco.frozenup.entity;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.teamdraco.frozenup.FrozenUp;
import net.teamdraco.frozenup.init.FrozenUpEntities;
import net.teamdraco.frozenup.init.FrozenUpItems;
import net.teamdraco.frozenup.init.FrozenUpSoundEvents;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

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

    @Override
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

    @Override
    protected float getActiveEyeHeight(EntityPose poseIn, EntityDimensions sizeIn) {
        return this.isBaby() ? 0.5F : 0.7F;
    }

    public static DefaultAttributeContainer.Builder createChillooAttributes() {
        return MobEntity.createMobAttributes()
                        .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
                        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.WHEAT_SEEDS || item == Items.BEETROOT_SEEDS || item == Items.MELON_SEEDS || item == Items.COCOA_BEANS || item == Items.POTATO || item == Items.CARROT;
    }

    @Nullable
    public DyeColor getBandColor() {
        byte color = dataTracker.get(COLOR);
        return color == -1 || color >= 16 ? null : DyeColor.byId(color);
    }
    public void setBandColor(DyeColor color) {
        dataTracker.set(COLOR, color == null ? -1 : (byte) color.ordinal());
    }

    @Nullable
    public DyeColor getSweaterColor() {
        byte color = dataTracker.get(COLOR);
        return color < 16 ? null : DyeColor.byId(color - 16);
    }
    public void setSweaterColor(DyeColor color) {
        dataTracker.set(COLOR, color == null ? -1 : (byte) (color.ordinal() + 16));
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.setBandColor(DyeColor.RED);
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(12.0D);
        }
    }

    @Override
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
        } else if (digTimer > 0) {
            digTimer--;
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity ageable) {
        ChillooEntity entity = FrozenUpEntities.CHILLOO.create(this.world);
        if (entity != null) {
            entity.setOwnerUuid(this.getOwnerUuid());
            entity.setTamed(true);
        }
        return entity;
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
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COLOR, (byte) 0);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound compound) {
        compound.putByte("Colors", this.dataTracker.get(COLOR));
        compound.putInt("FeatherLayTime", this.timeUntilNextFeather);

        return super.writeNbt(compound);
    }
    @Override
    public void readNbt(NbtCompound compound) {
        super.readNbt(compound);
        this.dataTracker.set(COLOR, compound.getByte("Colors"));
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

    static class DiggingGoal extends Goal {
        private static final Identifier DIGGING_LOOT = new Identifier(FrozenUp.MOD_ID, "entities/chilloo_digging");
        private static final Predicate<BlockState> IS_GRASS = BlockStatePredicate.forBlock(Blocks.GRASS);

        private final ChillooEntity entity;
        private int eatingGrassTimer;
        private int digTimer;

        public DiggingGoal(ChillooEntity entity) {
            this.entity = entity;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK, Control.JUMP));
        }

        @Override
        public boolean canStart() {
            if (digTimer > 0) {
                --digTimer;
                return false;
            } else if (entity.isInSittingPose() || entity.getRandom().nextInt(entity.isBaby() ? 100 : 1000) != 0) {
                return false;
            } else {
                BlockPos pos = entity.getBlockPos();
                return IS_GRASS.test(entity.world.getBlockState(pos)) || IS_GRASS.test(entity.world.getBlockState(pos.down()));
            }
        }

        @Override
        public void start() {
            this.eatingGrassTimer = 40;
            this.digTimer = 6000;
            this.entity.world.sendEntityStatus(entity, (byte) 10);
            this.entity.getNavigation().stop();
        }

        @Override
        public void stop() {
            eatingGrassTimer = 0;
        }

        @Override
        public boolean shouldContinue() {
            return eatingGrassTimer > 0;
        }

        @Override
        public void tick() {
            if (digTimer > 0) {
                --digTimer;
            }

            if (eatingGrassTimer > 0) {
                --eatingGrassTimer; if (eatingGrassTimer == 25) {
                    BlockPos pos = entity.getBlockPos();
                    if (IS_GRASS.test(entity.world.getBlockState(pos))) {
                        entity.onEatingGrass();
                    } else {
                        BlockPos downPos = pos.down();
                        if (entity.world.getBlockState(downPos).isOf(Blocks.GRASS_BLOCK)) {
                            entity.onEatingGrass();
                            entity.world.syncWorldEvent(2001, downPos, Block.getRawIdFromState(Blocks.GRASS_BLOCK.getDefaultState()));

                            MinecraftServer server = entity.world.getServer();
                            if (server != null) {
                                List<ItemStack> items = server.getLootManager().getTable(DIGGING_LOOT).generateLoot(new LootContext.Builder((ServerWorld) entity.world).random(entity.getRandom()).build(LootContextTypes.EMPTY));
                                ItemScatterer.spawn(entity.world, pos, DefaultedList.copyOf(ItemStack.EMPTY, items.toArray(new ItemStack[0])));
                            }
                        }
                    }
                }
            }
        }
    }
}
