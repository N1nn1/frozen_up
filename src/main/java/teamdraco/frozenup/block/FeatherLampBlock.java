package teamdraco.frozenup.block;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class FeatherLampBlock extends Block {
    public static final BooleanProperty LIT = Properties.LIT;

    public FeatherLampBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LIT, Boolean.FALSE));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(LIT)) {
            this.powerBlockOff(state, world, pos);
        } else {
            this.powerBlockOn(state, world, pos);
        }
        this.playSound(player, world, pos);
        return ActionResult.success(world.isClient);
    }
    
    public void powerBlockOn(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, state.with(LIT, Boolean.TRUE), 3);
    }
    
    public void powerBlockOff(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, state.with(LIT, Boolean.FALSE), 3);
    }
    
    protected void playSound(@Nullable PlayerEntity playerIn, WorldAccess world, BlockPos pos) {
        world.playSound(playerIn, pos, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.5F);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }
}
