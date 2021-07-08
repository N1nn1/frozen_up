package net.teamdraco.frozenup.item;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.teamdraco.frozenup.util.Util;

public class HotChocolateMugItem extends AbstractDrinkableMugItem {
    public HotChocolateMugItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void applyEffects(ItemStack stack, World world, LivingEntity user) {
        Util.removeEntityEffects(user, instance -> instance.getEffectType().getType() == StatusEffectType.HARMFUL);
    }
}
