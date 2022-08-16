package com.ninni.frozenup.block;

import com.ninni.frozenup.init.FrozenUpItems;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class FiberCoveringBlock extends GlowLichenBlock {

    public FiberCoveringBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public boolean canBeReplaced(BlockState p_153299_, BlockPlaceContext context) {
        return !context.getItemInHand().is(FrozenUpItems.CHILLOO_FEATHER_COVERING.get()) || super.canBeReplaced(p_153299_, context);
    }

}
