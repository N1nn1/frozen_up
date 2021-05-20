package teamdraco.frozenup.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class MugBlock extends Block {
    protected static final VoxelShape MUG = createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 9.0D, 12.0D);
    protected static final VoxelShape HANDLE = createCuboidShape(7.0D, 2.5D, 2.0D, 9.0D, 7.5D, 4.0D);
    protected static final VoxelShape SHAPE = VoxelShapes.union(MUG, HANDLE);

    public MugBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
