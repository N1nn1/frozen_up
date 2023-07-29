package com.ninni.frozenup.block;


import com.ninni.frozenup.criterion.FrozenUpCriteria;
import com.ninni.frozenup.util.Util;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

@SuppressWarnings("deprecation")
public class TruffleCakeBlock extends Block {
    public static final IntProperty BITES = Properties.BITES;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 9, 14);
    protected static final VoxelShape ONE_BITE = VoxelShapes.combineAndSimplify(Block.createCuboidShape(8, 0, 2, 14, 9, 8), Block.createCuboidShape(2, 0, 8, 14, 9, 14), BooleanBiFunction.OR);
    protected static final VoxelShape TWO_BITES = Block.createCuboidShape(2, 0, 8, 14, 9, 14);
    protected static final VoxelShape THREE_BITES = Block.createCuboidShape(8, 0, 8, 14, 9, 14);

    public TruffleCakeBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(BITES, 0));
    }

    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(BITES) == 1) return ONE_BITE;
        if (state.get(BITES) == 2) return TWO_BITES;
        if (state.get(BITES) == 3) return THREE_BITES;
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (world.isClient) {
            if (tryEat(world, pos, state, player).isAccepted()) return ActionResult.SUCCESS;
            if (itemStack.isEmpty()) return ActionResult.CONSUME;
        }
        return tryEat(world, pos, state, player);
    }

    protected static ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canConsume(false)) return ActionResult.PASS;
        else {
            player.incrementStat(Stats.EAT_CAKE_SLICE);
            player.getHungerManager().add(5, 0.4F);
            if (player instanceof ServerPlayerEntity serverPlayer && Util.removeEntityEffects(player, effect -> effect.getCategory() == StatusEffectCategory.HARMFUL)) FrozenUpCriteria.CURE_HARMFUL_STATUS_EFFECTS.trigger(serverPlayer);
            player.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1, 1);
            world.emitGameEvent(player, GameEvent.EAT, pos);

            if (state.get(BITES) < 3) world.setBlockState(pos, state.with(BITES, state.get(BITES) + 1), 3);
             else {
                world.removeBlock(pos, false);
                world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }

            return ActionResult.SUCCESS;
        }
    }

    @Override public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) { return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos); }
    @Override public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) { return world.getBlockState(pos.down()).isSolid(); }
    @Override protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(BITES); }
    @Override public int getComparatorOutput(BlockState state, World world, BlockPos pos) { return getComparatorOutput(state.get(BITES)); }
    public static int getComparatorOutput(int bites) { return (7 - bites) * 2; }
    @Override public boolean hasComparatorOutput(BlockState state) { return true; }
    @Override public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) { return false; }
}
