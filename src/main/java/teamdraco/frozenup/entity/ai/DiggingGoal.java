package teamdraco.frozenup.entity.ai;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import teamdraco.frozenup.FrozenUp;
import teamdraco.frozenup.entity.ChillooEntity;

public class DiggingGoal extends Goal {
    private static final Identifier DIGGING_LOOT = new Identifier(FrozenUp.MOD_ID, "entities/chilloo_digging");
    private static final Predicate<BlockState> IS_GRASS = BlockStatePredicate.forBlock(Blocks.GRASS);

    private final ChillooEntity entity;
    private int eatingGrassTimer;
    private int digTimer;

    public DiggingGoal(ChillooEntity entity) {
        this.entity = entity;
        setControls(EnumSet.of(Control.MOVE, Control.LOOK, Control.JUMP));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    @Override
    public boolean canStart() {
        if (digTimer > 0) {
            --digTimer;
            return false;
        }
        if (entity.isInSittingPose()) return false;
        if (entity.getRandom().nextInt(entity.isBaby() ? 100 : 1000) != 0) return false;
        else {
            BlockPos blockpos = entity.getBlockPos();
            if (IS_GRASS.test(entity.world.getBlockState(blockpos))) return true;
            else return entity.world.getBlockState(blockpos.down()).isOf(Blocks.GRASS_BLOCK);
        }
    }

    @Override
    public void start() {
        eatingGrassTimer = 40;
        digTimer = 6000;
        entity.world.sendEntityStatus(entity, (byte) 10);
        entity.getNavigation().stop();
    }

    @Override
    public void stop() {
        eatingGrassTimer = 0;
    }

    @Override
    public boolean shouldContinue() {
        return eatingGrassTimer > 0;
    }

    public int timer() {
        return eatingGrassTimer;
    }

    @Override
    public void tick() {
        if (digTimer > 0) {
            --digTimer;
        }
        if (eatingGrassTimer > 0) --eatingGrassTimer;
        if (eatingGrassTimer == 25) {
            BlockPos blockpos = entity.getBlockPos();
            if (IS_GRASS.test(entity.world.getBlockState(blockpos))) entity.onEatingGrass();
            else {
                BlockPos blockpos1 = blockpos.down();
                if (entity.world.getBlockState(blockpos1).isOf(Blocks.GRASS_BLOCK)) {
                    entity.onEatingGrass();
                    entity.world.syncWorldEvent(2001, blockpos1, Block.getRawIdFromState(Blocks.GRASS_BLOCK.getDefaultState()));
                    List<ItemStack> items = entity.world.getServer().getLootManager().getTable(DIGGING_LOOT).generateLoot(new LootContext.Builder((ServerWorld) entity.world).random(entity.getRandom()).build(LootContextTypes.EMPTY));
                    ItemScatterer.spawn(entity.world, blockpos, DefaultedList.copyOf(ItemStack.EMPTY, items.toArray(new ItemStack[0])));
                }
            }
        }
    }
}
