package net.teamdraco.frozenup.entity.ai.goal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.teamdraco.frozenup.FrozenUp;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class DigInGrassGoal extends Goal {
    private static final Predicate<BlockState> GRASS_PREDICATE;
    private static final Identifier DIGGING_LOOT = new Identifier(FrozenUp.MOD_ID, "entities/chilloo_digging");
    private final MobEntity mob;
    private final World world;
    private int timer;

    public DigInGrassGoal(MobEntity mob) {
        this.mob = mob;
        this.world = mob.world;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK, Control.JUMP));
    }

    @Override
    public boolean canStart() {
        if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos blockPos = this.mob.getBlockPos();
            if (GRASS_PREDICATE.test(this.world.getBlockState(blockPos))) {
                return true;
            } else {
                return this.world.getBlockState(blockPos.down()).isIn(BlockTags.DIRT);
            }
        }
    }

    @Override
    public void start() {
        this.timer = 40;
        this.world.sendEntityStatus(this.mob, (byte)10);
        this.mob.getNavigation().stop();
    }

    @Override
    public void stop() {
        this.timer = 0;
    }

    @Override
    public boolean shouldContinue() {
        return this.timer > 0;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        this.timer = Math.max(0, this.timer - 1);
        if (this.timer == 4) {
            BlockPos pos = this.mob.getBlockPos();
            if (GRASS_PREDICATE.test(this.world.getBlockState(pos))) {
                this.mob.onEatingGrass();
            } else {
                BlockPos downPos = pos.down();
                if (this.world.getBlockState(downPos).isIn(BlockTags.DIRT)) {
                    if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                        this.world.syncWorldEvent(2001, downPos, Block.getRawIdFromState(Blocks.DIRT.getDefaultState()));
                        List<ItemStack> items = this.world.getServer().getLootManager().getTable(DIGGING_LOOT).generateLoot(new LootContext.Builder((ServerWorld) this.world).random(this.world.getRandom()).build(LootContextTypes.EMPTY));
                        ItemScatterer.spawn(this.world, pos, DefaultedList.copyOf(ItemStack.EMPTY, items.toArray(new ItemStack[0])));
                    }


                    this.mob.onEatingGrass();
                }
            }

        }
    }

    static {
        GRASS_PREDICATE = BlockStatePredicate.forBlock(Blocks.GRASS);
    }
}
