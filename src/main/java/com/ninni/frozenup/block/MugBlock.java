package com.ninni.frozenup.block;

import com.ninni.frozenup.init.FrozenUpBlocks;
import com.ninni.frozenup.init.FrozenUpSoundEvents;
import com.ninni.frozenup.item.AbstractDrinkableMugItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class MugBlock extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected static final VoxelShape MUG = box(4.0D, 0.0D, 4.0D, 12.0D, 9.0D, 12.0D);
    protected static final VoxelShape HANDLE = box(7.0D, 2.5D, 2.0D, 9.0D, 7.5D, 4.0D);
    protected static final VoxelShape SHAPE = Shapes.or(MUG, HANDLE);
    protected static final VoxelShape NORTH_SHAPE = Shapes.or(MUG, HANDLE);
    protected static final VoxelShape SOUTH_SHAPE = Shapes.or(Block.box(7, 2.5, 12, 9, 7.5, 14), Block.box(4, 0, 4, 12, 9, 12));
    protected static final VoxelShape EAST_SHAPE = Shapes.or(Block.box(12, 2.5, 7, 14, 7.5, 9), Block.box(4, 0, 4, 12, 9, 12));
    protected static final VoxelShape WEST_SHAPE = Shapes.or(Block.box(2, 2.5, 7, 4, 7.5, 9), Block.box(4, 0, 4, 12, 9, 12));

    @Nullable private final Supplier<Item> mugItem;

    public MugBlock(@Nullable Supplier<Item> mugItem, Properties settings) {
        super(settings);
        this.registerDefaultState((this.stateDefinition.any()).setValue(FACING, Direction.NORTH));

        this.mugItem = mugItem;
    }
    public MugBlock(Properties settings) {
        this(null, settings);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        Item item = mugItem == null ? null : mugItem.get();
        if (item instanceof AbstractDrinkableMugItem && !state.is(FrozenUpBlocks.EMPTY_MUG.get())) {
            item.finishUsingItem(new ItemStack(item), world, player);
            world.setBlockAndUpdate(pos, FrozenUpBlocks.EMPTY_MUG.get().defaultBlockState().setValue(FACING, state.getValue(FACING)));
            if (!world.isClientSide) {
                world.playSound(null, player, FrozenUpSoundEvents.ITEM_MUG_DRINK.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            }

            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST ->  EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> SHAPE;
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState state = super.getStateForPlacement(ctx);
        Player player = ctx.getPlayer();
        HumanoidArm arm = player != null ? player.getMainArm() : HumanoidArm.RIGHT;
        HumanoidArm activeArm = ctx.getHand() == InteractionHand.MAIN_HAND ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
        Direction facing = ctx.getHorizontalDirection();
        return state == null ? null : state.setValue(FACING, (arm == HumanoidArm.RIGHT && activeArm == HumanoidArm.RIGHT) || (arm == HumanoidArm.LEFT && activeArm == HumanoidArm.LEFT) ? facing.getClockWise() : facing.getCounterClockWise());
    }
}
