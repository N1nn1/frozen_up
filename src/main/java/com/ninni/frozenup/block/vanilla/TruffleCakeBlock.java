package com.ninni.frozenup.block.vanilla;


import com.ninni.frozenup.init.FrozenUpCriteriaTriggers;
import com.ninni.frozenup.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class TruffleCakeBlock extends Block {
    public static final IntegerProperty BITES = BlockStateProperties.BITES;
    protected static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 9, 14);
    protected static final VoxelShape ONE_BITE = Shapes.join(Block.box(8, 0, 2, 14, 9, 8), Block.box(2, 0, 8, 14, 9, 14), BooleanOp.OR);
    protected static final VoxelShape TWO_BITES = Block.box(2, 0, 8, 14, 9, 14);
    protected static final VoxelShape THREE_BITES = Block.box(8, 0, 8, 14, 9, 14);

    public TruffleCakeBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((this.stateDefinition.any()).setValue(BITES, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        if (state.getValue(BITES) == 1) return ONE_BITE;
        if (state.getValue(BITES) == 2) return TWO_BITES;
        if (state.getValue(BITES) == 3) return THREE_BITES;
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (world.isClientSide()) {
            if (tryEat(world, pos, state, player).consumesAction()) return InteractionResult.SUCCESS;
            if (itemStack.isEmpty()) return InteractionResult.CONSUME;
        }
        return tryEat(world, pos, state, player);
    }

    protected static InteractionResult tryEat(LevelAccessor world, BlockPos pos, BlockState state, Player player) {
        if (!player.canEat(false)) return InteractionResult.PASS;
        else {
            player.awardStat(Stats.EAT_CAKE_SLICE);
            player.getFoodData().eat(3, 0.3F);
            Util.removeEntityEffects(player, instance -> instance.getEffect().getCategory() == MobEffectCategory.HARMFUL);
            if (player instanceof ServerPlayer serverPlayer && Util.removeEntityEffects(player, mobEffectInstance ->  mobEffectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL)) {
                FrozenUpCriteriaTriggers.CURE_HARMFUL_STATUS_EFFECTS.trigger(serverPlayer);
            }
            player.playSound(SoundEvents.GENERIC_EAT, 1, 1);
            world.gameEvent(player, GameEvent.EAT, pos);

            if (state.getValue(BITES) < 3) world.setBlock(pos, state.setValue(BITES, state.getValue(BITES) + 1), 3);
             else {
                world.removeBlock(pos, false);
                world.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }

            return InteractionResult.SUCCESS;
        }
    }

    @Override public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) { return direction == Direction.DOWN && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos); }
    @Override public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) { return world.getBlockState(pos.below()).isSolid(); }
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) { builder.add(BITES); }
    @Override public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) { return getComparatorOutput(state.getValue(BITES)); }
    public static int getComparatorOutput(int bites) { return (7 - bites) * 2; }
    @Override public boolean hasAnalogOutputSignal(BlockState state) { return true; }
    @Override public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) { return false; }

}
