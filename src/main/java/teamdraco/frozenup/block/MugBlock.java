package teamdraco.frozenup.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class MugBlock extends HorizontalFacingBlock {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    protected static final VoxelShape MUG = createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 9.0D, 12.0D);
    protected static final VoxelShape HANDLE = createCuboidShape(7.0D, 2.5D, 2.0D, 9.0D, 7.5D, 4.0D);
    protected static final VoxelShape SHAPE = VoxelShapes.union(MUG, HANDLE);
    protected static final VoxelShape NORTH_SHAPE = VoxelShapes.union(MUG, HANDLE);
    protected static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(Block.createCuboidShape(7, 2.5, 12, 9, 7.5, 14), Block.createCuboidShape(4, 0, 4, 12, 9, 12));
    protected static final VoxelShape EAST_SHAPE = VoxelShapes.union(Block.createCuboidShape(12, 2.5, 7, 14, 7.5, 9), Block.createCuboidShape(4, 0, 4, 12, 9, 12));
    protected static final VoxelShape WEST_SHAPE = VoxelShapes.union(Block.createCuboidShape(2, 2.5, 7, 4, 7.5, 9), Block.createCuboidShape(4, 0, 4, 12, 9, 12));

    public MugBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        boolean isSneaking = ctx.getPlayer().isSneaking();
        if (isSneaking) {
            return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
        }
            return null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
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
}
