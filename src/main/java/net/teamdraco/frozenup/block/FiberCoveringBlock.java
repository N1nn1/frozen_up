package net.teamdraco.frozenup.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.GlowLichenBlock;
import net.minecraft.item.ItemPlacementContext;
import net.teamdraco.frozenup.init.FrozenUpItems;

public class FiberCoveringBlock extends GlowLichenBlock {
    public FiberCoveringBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.getStack().isOf(FrozenUpItems.CHILLOO_FEATHER_COVERING) || super.canReplace(state, context);
    }
}
