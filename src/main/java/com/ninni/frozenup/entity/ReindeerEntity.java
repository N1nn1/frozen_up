package com.ninni.frozenup.entity;

import com.ninni.frozenup.client.screen.ReindeerInventoryMenu;
import com.ninni.frozenup.init.FrozenUpCriteriaTriggers;
import com.ninni.frozenup.init.FrozenUpEnchantments;
import com.ninni.frozenup.init.FrozenUpItemTags;
import com.ninni.frozenup.init.FrozenUpItems;
import com.ninni.frozenup.init.FrozenUpSoundEvents;
import com.ninni.frozenup.network.FrozenUpNetworkHandler;
import com.ninni.frozenup.network.OpenReindeerScreenPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RunAroundLikeCrazyGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ReindeerEntity extends AbstractHorse {
    private static final EntityDataAccessor<Boolean> CAN_CLOUD_JUMP = SynchedEntityData.defineId(ReindeerEntity.class, EntityDataSerializers.BOOLEAN);
    public static final Ingredient TEMPT_INGREDIENT = Ingredient.of(FrozenUpItemTags.REINDEER_TEMPTS);
    private static final UUID HASTY_HOOVES_SPEED_BOOST_ID = UUID.fromString("d9f1b970-be2b-4d4b-8978-e9f54bc1b04e");

    public ReindeerEntity(EntityType<? extends AbstractHorse> type, Level world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 0.85));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.6, 1.8));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, TEMPT_INGREDIENT, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.85));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CAN_CLOUD_JUMP, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("CanCloudJump", this.entityData.get(CAN_CLOUD_JUMP));
        if (!this.inventory.getItem(1).isEmpty()) {
            pCompound.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(CAN_CLOUD_JUMP, pCompound.getBoolean("CanCloudJump"));
        if (pCompound.contains("ArmorItem", 10)) {
            ItemStack itemstack = ItemStack.of(pCompound.getCompound("ArmorItem"));
            if (!itemstack.isEmpty() && this.isArmor(itemstack)) {
                this.inventory.setItem(1, itemstack);
            }
        }
    }

    @Override
    protected void updateContainerEquipment() {
        if (!this.level().isClientSide()) {
            super.updateContainerEquipment();
            this.equipArmor(this.inventory.getItem(1));
            this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        }
    }

    public boolean hasCloudJumpData() {
        return this.entityData.get(CAN_CLOUD_JUMP);
    }

    @Override
    public void tick() {
        super.tick();

        this.setDiscardFriction(this.canCloudJump() && !this.isLeashed());

        if (!level().isClientSide()) {
            boolean flag = this.level().isNight();
            this.entityData.set(CAN_CLOUD_JUMP, flag);
        }
        if (this.level().isClientSide() && this.canCloudJump() && this.getDeltaMovement().lengthSqr() > 0.03 ) {
            Vec3 vec3d = this.getViewVector(0.0f);
            float f = Mth.cos(this.getYRot() * ((float)Math.PI / 180)) * 0.3f;
            float g = Mth.sin(this.getYRot() * ((float)Math.PI / 180)) * 0.3f;
            float h = 1.2f - this.random.nextFloat() * 0.7f;
            for (int i = 0; i < 2; ++i) {
                this.level().addParticle(ParticleTypes.END_ROD, this.getX() - vec3d.x * (double)h * 0.75 + (double)f, this.getY() - vec3d.y + 1, this.getZ() - vec3d.z * (double)h + (double)g, 0.0, 0.0, 0.0);
                this.level().addParticle(ParticleTypes.END_ROD, this.getX() - vec3d.x * (double)h * 0.75 - (double)f, this.getY() - vec3d.y + 1, this.getZ() - vec3d.z * (double)h - (double)g, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.canCloudJump()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0, 0.07, 0));
        }
        super.travel(pTravelVector);
    }

    @Override
    public boolean canWearArmor() {
        return true;
    }

    private void equipArmor(ItemStack stack) {
        this.setItemSlot(EquipmentSlot.CHEST, stack);
        this.setDropChance(EquipmentSlot.CHEST, 0.0f);
    }

    public static AttributeSupplier.Builder createReindeerAttributes() {
        return createBaseHorseAttributes()
            .add(Attributes.MOVEMENT_SPEED, 0.175)
            .add(Attributes.JUMP_STRENGTH, 0.4)
            .add(Attributes.MAX_HEALTH, 16.0D);
    }

    @Override
    protected void onChangedBlock(BlockPos pos) {
        if (this.isArmor(this.getItemBySlot(EquipmentSlot.CHEST)) && this.isVehicle() && this.onGround()) {
            if (EnchantmentHelper.getItemEnchantmentLevel(FrozenUpEnchantments.HASTY_HOOVES.get(), this.getItemBySlot(EquipmentSlot.CHEST)) > 0) { this.addHastyHoovesEnchantment(); }
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FROST_WALKER, this.getItemBySlot(EquipmentSlot.CHEST)) > 0) { FrostWalkerEnchantment.onEntityMoved(this, this.level(), pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FROST_WALKER, this)); }
        } else { this.removeHastyHoovesSpeedBoost(); }
    }

    protected void removeHastyHoovesSpeedBoost() {
        AttributeInstance entityAttributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (entityAttributeInstance != null) { if (entityAttributeInstance.getModifier(HASTY_HOOVES_SPEED_BOOST_ID) != null) { entityAttributeInstance.removeModifier(HASTY_HOOVES_SPEED_BOOST_ID); } }
    }

    protected void addHastyHoovesEnchantment() {
        if (hasHastyHooves(this.getItemBySlot(EquipmentSlot.CHEST))) {
            AttributeInstance speed = this.getAttribute(Attributes.MOVEMENT_SPEED);
            float level = getHastyHooves(this);
            if (speed != null && level == 1) {
                AttributeModifier attributeModifier = new AttributeModifier(HASTY_HOOVES_SPEED_BOOST_ID, "Hasty hooves speed boost", 0.025, AttributeModifier.Operation.ADDITION);
                if (!speed.hasModifier(attributeModifier)) { speed.addTransientModifier(attributeModifier); }
            }
            if (speed != null && level == 2) {
                AttributeModifier attributeModifier = new AttributeModifier(HASTY_HOOVES_SPEED_BOOST_ID, "Hasty hooves speed boost", 0.075, AttributeModifier.Operation.ADDITION);
                if (!speed.hasModifier(attributeModifier)) { speed.addTransientModifier(attributeModifier); }
            }
        }
    }

    public boolean hasCloudJumper(ItemStack stack) { return EnchantmentHelper.getItemEnchantmentLevel(FrozenUpEnchantments.CLOUD_JUMPER.get(), stack) > 0; }
    public boolean canCloudJump() { return this.hasCloudJumpData() && this.hasCloudJumper(getItemBySlot(EquipmentSlot.CHEST)) && this.level().getBlockState(this.blockPosition().below(3)).is(Blocks.AIR) && this.level().getBlockState(this.blockPosition().below(2)).is(Blocks.AIR) && this.level().getBlockState(this.blockPosition().below(1)).is(Blocks.AIR) && !this.onGround(); }
    public boolean hasHastyHooves(ItemStack stack) { return EnchantmentHelper.getItemEnchantmentLevel(FrozenUpEnchantments.HASTY_HOOVES.get(), stack) > 0; }
    public static int getHastyHooves(LivingEntity entity) { return EnchantmentHelper.getEnchantmentLevel(FrozenUpEnchantments.HASTY_HOOVES.get(), entity); }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (this.isTamed() && player.isSecondaryUseActive()) {
            this.openCustomInventoryScreen(player);
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }

        if (this.isVehicle()) { return super.mobInteract(player, hand); }

        if (!itemStack.isEmpty()) {
            if (this.isFood(itemStack)) { return this.interactReindeer(player, itemStack); }

            InteractionResult actionResult = itemStack.interactLivingEntity(player, this, hand);
            if (actionResult.consumesAction()) { return actionResult; }

            if (!this.isTamed()) {
                this.makeMad();
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }

            boolean bl = !this.isSaddled() && itemStack.is(Items.SADDLE);
            if (bl) {
                this.openCustomInventoryScreen(player);
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
        }
        this.doPlayerRide(player);
        return InteractionResult.sidedSuccess(this.level().isClientSide());
    }

    @Override
    public void openCustomInventoryScreen(Player player) {
        if (!this.level().isClientSide && (!this.isVehicle() || this.hasPassenger(player)) && this.isTamed() && player instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.containerMenu != serverPlayer.inventoryMenu)
                serverPlayer.closeContainer();

            serverPlayer.nextContainerCounter();
            FrozenUpNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new OpenReindeerScreenPacket(serverPlayer.containerCounter, this.getInventorySize(), this.getId()));
            serverPlayer.containerMenu = new ReindeerInventoryMenu(serverPlayer.containerCounter, serverPlayer.getInventory(), this.inventory, this);
            serverPlayer.initMenu(serverPlayer.containerMenu);
            MinecraftForge.EVENT_BUS.post(new PlayerContainerEvent.Open(serverPlayer, serverPlayer.containerMenu));
        }
    }

    public SimpleContainer getInventory() {
        return this.inventory;
    }

    public InteractionResult interactReindeer(Player player, ItemStack stack) {
        boolean bl = this.handleEating(player, stack);
        if (!player.getAbilities().instabuild) { stack.shrink(1); }
        if (this.level().isClientSide()) { return InteractionResult.CONSUME; } else { return bl ? InteractionResult.SUCCESS : InteractionResult.PASS; }
    }

    @Override
    protected boolean handleEating(Player player, ItemStack item) {
        boolean bl = false;
        float f = 0.0F;
        int j = 0;
        if (item.is(Items.WHEAT)) {
            f = 2.0F;
            j = 2;
        } else if (item.is(Items.CARROT)) {
            f = 2.0F;
            j = 3;
        } else if (item.is(Items.SUGAR)) {
            f = 1.0F;
            j = 1;
        } else if (item.is(Blocks.GLOW_LICHEN.asItem())) {
            f = 20.0F;
            j = 15;
        } else if (item.is(Items.APPLE)) {
            f = 3.0F;
            j = 3;
        } else if (item.is(Items.GOLDEN_CARROT)) {
            f = 4.0F;
            j = 6;
        } else if (item.is(Items.GOLDEN_APPLE) || item.is(Items.ENCHANTED_GOLDEN_APPLE)) {
            f = 10.0F;
            j = 10;
        }

        if (this.getHealth() < this.getMaxHealth() && f > 0.0F) {
            this.heal(f);
            bl = true;
        }

        if (j > 0 && (bl || !this.isTamed()) && this.getTemper() < this.getMaxTemper()) {
            bl = true;
            if (!this.level().isClientSide()) { this.modifyTemper(j); }
        }

        if (bl) {
            this.playEatingAnimation();
            this.gameEvent(GameEvent.EAT);
        }

        return bl;
    }

    private void playEatingAnimation() {
        this.setEating();
        if (!this.isSilent()) {
            SoundEvent soundevent = this.getEatingSound();
            if (soundevent != null) { this.level().playSound(null, this.getX(), this.getY(), this.getZ(), soundevent, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F); }
        }
    }

    @Override
    public boolean isArmor(ItemStack pStack) {
        return pStack.getItem() == FrozenUpItems.HOOF_ARMOR.get();
    }

    private void setEating() { if (!this.level().isClientSide()) { this.setFlag(64, true); } }

    @Override
    public void positionRider(Entity passenger, MoveFunction moveFunction) {
        if (this.hasPassenger(passenger)) {
            float f = Mth.cos(this.yBodyRot * 0.0175F);
            float g = Mth.sin(this.yBodyRot * 0.0175F);
            passenger.setPos(this.getX() + (double)(0.3F * g), this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset() + 0.05F, this.getZ() - (double)(0.3F * f));
        }
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.getBbHeight() * 0.7;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return TEMPT_INGREDIENT.test(pStack);
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return !this.hasCloudJumper(this.getItemBySlot(EquipmentSlot.CHEST)) && super.causeFallDamage(pFallDistance, pMultiplier, pSource);
    }

    @Override
    protected void playGallopSound(SoundType type) {
        this.playSound(FrozenUpSoundEvents.ENTITY_REINDEER_GALLOP.get(), type.getVolume() * 0.15F, type.getPitch());
        if (this.random.nextInt(10) == 0) {
            this.playSound(SoundEvents.HORSE_BREATHE, type.getVolume() * 0.6F, type.getPitch());
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        super.getAmbientSound();
        return FrozenUpSoundEvents.ENTITY_REINDEER_AMBIENT.get();
    }
    @Override
    protected SoundEvent getDeathSound() {
        super.getDeathSound();
        return FrozenUpSoundEvents.ENTITY_REINDEER_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getEatingSound() {
        return FrozenUpSoundEvents.ENTITY_REINDEER_EAT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        super.getHurtSound(source);
        return FrozenUpSoundEvents.ENTITY_REINDEER_HURT.get();
    }
    @Override
    protected SoundEvent getAngrySound() {
        super.getAngrySound();
        return FrozenUpSoundEvents.ENTITY_REINDEER_ANGRY.get();
    }

    @Override
    protected void playJumpSound() {
        if (canCloudJump()) this.playSound(FrozenUpSoundEvents.ENTITY_REINDEER_CLOUD_JUMP.get(), 1F, 1.0F);
        else this.playSound(FrozenUpSoundEvents.ENTITY_REINDEER_JUMP.get(), 0.25F, 1.0F);
    }

    @Override
    public void handleStartJump(int pJumpPower) {
        super.handleStartJump(pJumpPower);
        if (!this.level().isClientSide() && canCloudJump()) {
            FrozenUpCriteriaTriggers.CLOUD_JUMPER_BOOST.trigger((ServerPlayer) this.getControllingPassenger());
        }
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType <ReindeerEntity> entity, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random){
        BlockState state = world.getBlockState(pos.below());
        return state.is(Blocks.GRASS_BLOCK) && world.getRawBrightness(pos, 0) > 8;
    }
}
