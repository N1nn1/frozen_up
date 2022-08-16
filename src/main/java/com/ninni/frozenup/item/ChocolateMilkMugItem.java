package com.ninni.frozenup.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ChocolateMilkMugItem extends AbstractDrinkableMugItem {

    public ChocolateMilkMugItem(Block block, Properties settings) { super(block, settings); }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        user.removeAllEffects();
        if (!world.isClientSide) {
            user.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 10 * 20, 0));
            user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10 * 20, 1));
        }
        return super.finishUsingItem(stack, world, user);
    }

}
