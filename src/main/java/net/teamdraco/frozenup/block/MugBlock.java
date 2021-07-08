package net.teamdraco.frozenup.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.teamdraco.frozenup.init.FrozenUpBlocks;
import net.teamdraco.frozenup.item.AbstractDrinkableMugItem;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class MugBlock extends HorizontalFacingBlock {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    protected static final VoxelShape MUG = createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 9.0D, 12.0D);
    protected static final VoxelShape HANDLE = createCuboidShape(7.0D, 2.5D, 2.0D, 9.0D, 7.5D, 4.0D);
    protected static final VoxelShape SHAPE = VoxelShapes.union(MUG, HANDLE);
    protected static final VoxelShape NORTH_SHAPE = VoxelShapes.union(MUG, HANDLE);
    protected static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(Block.createCuboidShape(7, 2.5, 12, 9, 7.5, 14), Block.createCuboidShape(4, 0, 4, 12, 9, 12));
    protected static final VoxelShape EAST_SHAPE = VoxelShapes.union(Block.createCuboidShape(12, 2.5, 7, 14, 7.5, 9), Block.createCuboidShape(4, 0, 4, 12, 9, 12));
    protected static final VoxelShape WEST_SHAPE = VoxelShapes.union(Block.createCuboidShape(2, 2.5, 7, 4, 7.5, 9), Block.createCuboidShape(4, 0, 4, 12, 9, 12));

    @Nullable private final Supplier<Item> mugItem;

    public MugBlock(@Nullable Supplier<Item> mugItem, Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));

        this.mugItem = mugItem;
    }
    public MugBlock(Settings settings) {
        this(null, settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Item item = mugItem == null ? null : mugItem.get();
        if (item instanceof AbstractDrinkableMugItem && !state.isOf(FrozenUpBlocks.EMPTY_MUG)) {
            item.finishUsing(player.getStackInHand(hand), world, player);
            world.setBlockState(pos, FrozenUpBlocks.EMPTY_MUG.getDefaultState().with(FACING, state.get(FACING)));
            if (!world.isClient) {
                world.playSoundFromEntity(null, player, SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.PLAYERS, 1.0f, 1.0f);
            }

            return ActionResult.success(world.isClient);
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch(state.get(FACING)) {
            case NORTH:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
                return SHAPE;
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        PlayerEntity player = ctx.getPlayer();
        Direction facing = ctx.getPlayerFacing();

        return state == null
            ? null
            : state.with(
                FACING,
                player != null && player.getMainArm() == Arm.RIGHT
                    ? facing.rotateYClockwise()
                    : facing.rotateYCounterclockwise()
            );
    }
}
