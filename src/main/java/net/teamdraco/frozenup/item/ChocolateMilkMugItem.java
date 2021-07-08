package net.teamdraco.frozenup.item;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ChocolateMilkMugItem extends MilkMugItem {
    public ChocolateMilkMugItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void applyEffects(ItemStack stack, World world, LivingEntity user) {
        super.applyEffects(stack, world, user);
        if (!world.isClient()) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 1));
        }
    }
}
