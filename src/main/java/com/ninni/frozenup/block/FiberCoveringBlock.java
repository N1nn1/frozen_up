package com.ninni.frozenup.block;

import com.ninni.frozenup.item.FrozenUpItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlowLichenBlock;
import net.minecraft.item.ItemPlacementContext;

public class FiberCoveringBlock extends GlowLichenBlock {
    public FiberCoveringBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.getStack().isOf(FrozenUpItems.CHILLOO_FEATHER_COVERING) || super.canReplace(state, context);
    }
}
