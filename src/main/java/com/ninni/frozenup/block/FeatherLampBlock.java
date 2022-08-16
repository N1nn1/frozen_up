package com.ninni.frozenup.block;

import com.ninni.frozenup.init.FrozenUpSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class FeatherLampBlock extends Block {
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public FeatherLampBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState state = super.getStateForPlacement(ctx);
        Player player = ctx.getPlayer();
        return state == null ? null : state.setValue(LIT, player != null && player.isShiftKeyDown());
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand p_60507_, BlockHitResult p_60508_) {
        boolean newLit = !state.getValue(LIT);

        world.setBlockAndUpdate(pos, state.setValue(LIT, newLit));
        if (!world.isClientSide) {
            world.playSound(null, player, newLit ? FrozenUpSoundEvents.BLOCK_CHILLOO_FEATHER_LAMP_TOGGLE_ON.get() : FrozenUpSoundEvents.BLOCK_CHILLOO_FEATHER_LAMP_TOGGLE_OFF.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        }

        return InteractionResult.SUCCESS;
    }

}
