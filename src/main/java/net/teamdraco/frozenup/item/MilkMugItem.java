package net.teamdraco.frozenup.item;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MilkMugItem extends AbstractDrinkableMugItem {
    public MilkMugItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void applyEffects(ItemStack stack, World world, LivingEntity user) {
        user.clearStatusEffects();
    }
}
