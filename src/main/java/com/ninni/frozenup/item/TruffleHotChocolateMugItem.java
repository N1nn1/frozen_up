package com.ninni.frozenup.item;

import com.ninni.frozenup.criterion.FrozenUpCriteria;
import com.ninni.frozenup.util.Util;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class TruffleHotChocolateMugItem extends AbstractDrinkableMugItem {
    public TruffleHotChocolateMugItem(Block block, Settings settings) { super(block, settings); }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        Util.removeEntityEffects(user, instance -> instance.getEffectType().getCategory() == StatusEffectCategory.HARMFUL);
        if (user instanceof ServerPlayerEntity) FrozenUpCriteria.CURE_HARMFUL_STATUS_EFFECTS.trigger((ServerPlayerEntity) user);

        if (!world.isClient) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 25 * 20, 1));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10 * 20, 1));
        }
        return super.finishUsing(stack, world, user);
    }
}
