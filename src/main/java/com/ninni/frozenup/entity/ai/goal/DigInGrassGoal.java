package com.ninni.frozenup.entity.ai.goal;

import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.entity.ChillooEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class DigInGrassGoal extends Goal {
    private static final Predicate<BlockState> GRASS_PREDICATE = BlockStatePredicate.forBlock(Blocks.GRASS);
    private static final ResourceLocation DIGGING_LOOT = new ResourceLocation(FrozenUp.MOD_ID, "entities/chilloo_digging");
    private final ChillooEntity chilloo;
    private final Level world;
    private int timer;

    public DigInGrassGoal(ChillooEntity chilloo) {
        this.chilloo = chilloo;
        this.world = chilloo.level;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.chilloo.getRandom().nextInt(1000) != 0 || !this.chilloo.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) return false;
        else {
            BlockPos blockPos = this.chilloo.blockPosition();
            if (GRASS_PREDICATE.test(this.world.getBlockState(blockPos))) return true;
            else return this.world.getBlockState(blockPos.below()).is(BlockTags.DIRT);
        }
    }

    @Override
    public void start() {
        this.timer = this.adjustedTickDelay(40);
        this.world.broadcastEntityEvent(this.chilloo, (byte)10);
        this.chilloo.getNavigation().stop();
    }

    public int getTimer() { return this.timer; }

    @Override public void stop() { this.timer = 0; }

    @Override
    public boolean canContinueToUse() {
        return this.timer > 0;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        this.timer = Math.max(0, this.timer - 1);
        if (this.timer == 4) {
            BlockPos pos = this.chilloo.blockPosition();
            if (GRASS_PREDICATE.test(this.world.getBlockState(pos))) this.chilloo.onDiggingInGrass();
            else {
                BlockPos downPos = pos.below();
                if (this.world.getBlockState(downPos).is(BlockTags.DIRT)) {
                    if (this.world.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                        this.world.levelEvent(2001, downPos, Block.getId(Blocks.DIRT.defaultBlockState()));
                        List<ItemStack> items = this.world.getServer().getLootTables().get(DIGGING_LOOT).getRandomItems(new LootContext.Builder((ServerLevel) this.world).withRandom(this.world.getRandom()).create(LootContextParamSets.EMPTY));
                        this.chilloo.setItemSlot(EquipmentSlot.MAINHAND, items.isEmpty() ? ItemStack.EMPTY : items.get(0));
                    }
                    this.chilloo.onDiggingInGrass();
                }
            }

        }
        if (!this.chilloo.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) stop();
    }
}
