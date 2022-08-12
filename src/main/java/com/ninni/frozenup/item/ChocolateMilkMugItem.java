package com.ninni.frozenup.item;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ChocolateMilkMugItem extends AbstractDrinkableMugItem {
    public ChocolateMilkMugItem(Block block, Settings settings) { super(block, settings); }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.clearStatusEffects();
        if (!world.isClient) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 10 * 20, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10 * 20, 1));
        }
        return super.finishUsing(stack, world, user);
    }
}
