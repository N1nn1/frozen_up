package teamdraco.frozenup.block;

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
import org.jetbrains.annotations.Nullable;

public class FeatherLampBlock extends Block{

	public static final BooleanProperty LIT = Properties.LIT;

	public FeatherLampBlock(Settings properties) {
		super(properties);
		this.setDefaultState(this.stateManager.getDefaultState().with(LIT, Boolean.FALSE));
		
	}

	public ActionResult onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult hit) {
		if (state.get(LIT)) {
			this.powerBlockOff(state, worldIn, pos);
		} else {
			this.powerBlockOn(state, worldIn, pos);
		}
		this.playSound(player, worldIn, pos);
		return ActionResult.success(worldIn.isClient);
	}
	
	public void powerBlockOn(BlockState state, World world, BlockPos pos) {
		world.setBlockState(pos, state.with(LIT, Boolean.TRUE), 3);
	}
	
	public void powerBlockOff(BlockState state, World world, BlockPos pos) {
		world.setBlockState(pos, state.with(LIT, Boolean.FALSE), 3);
	}
	
	protected void playSound(@Nullable PlayerEntity playerIn, WorldAccess worldIn, BlockPos pos) {
		worldIn.playSound(playerIn, pos, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.5F);
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}
	
}
