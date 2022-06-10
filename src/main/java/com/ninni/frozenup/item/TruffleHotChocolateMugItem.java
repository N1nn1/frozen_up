package com.ninni.frozenup.item;

import com.ninni.frozenup.util.Util;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TruffleHotChocolateMugItem extends AbstractDrinkableMugItem {
    public TruffleHotChocolateMugItem(Block block, Settings settings) { super(block, settings); }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        Util.removeEntityEffects(user, instance -> instance.getEffectType().getCategory() == StatusEffectCategory.HARMFUL);
        return super.finishUsing(stack, world, user);
    }
}
