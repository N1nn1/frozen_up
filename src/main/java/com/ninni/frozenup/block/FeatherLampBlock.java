package com.ninni.frozenup.block;

import com.ninni.frozenup.sound.FrozenUpSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class FeatherLampBlock extends Block {
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public FeatherLampBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LIT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        PlayerEntity player = ctx.getPlayer();
        return state == null ? null : state.with(LIT, player != null && player.isSneaking());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult result) {
        boolean newLit = !state.get(LIT);

        world.setBlockState(pos, state.with(LIT, newLit));
        if (!world.isClient) {
            world.playSoundFromEntity(null, player, newLit ? FrozenUpSoundEvents.BLOCK_CHILLOO_FEATHER_LAMP_TOGGLE_ON : FrozenUpSoundEvents.BLOCK_CHILLOO_FEATHER_LAMP_TOGGLE_OFF, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }

        return ActionResult.SUCCESS;
    }
}
