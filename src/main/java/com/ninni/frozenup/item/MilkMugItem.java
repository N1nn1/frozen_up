package com.ninni.frozenup.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class MilkMugItem extends AbstractDrinkableMugItem {

    public MilkMugItem(Block block, Item.Properties settings) { super(block, settings); }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        user.removeAllEffects();
        return super.finishUsingItem(stack, world, user);
    }

}
