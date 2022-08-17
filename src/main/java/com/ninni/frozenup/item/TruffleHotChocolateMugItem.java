package com.ninni.frozenup.item;

import com.ninni.frozenup.init.FrozenUpCriteriaTriggers;
import com.ninni.frozenup.util.Util;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class TruffleHotChocolateMugItem extends AbstractDrinkableMugItem {

    public TruffleHotChocolateMugItem(Block block, Properties settings) { super(block, settings); }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (entity instanceof ServerPlayer serverPlayer && Util.removeEntityEffects(entity, mobEffectInstance -> mobEffectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL)) {
            FrozenUpCriteriaTriggers.CURE_HARMFUL_STATUS_EFFECTS.trigger(serverPlayer);
        }
        if (!world.isClientSide()) {
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 25 * 20, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10 * 20, 1));
        }
        return super.finishUsingItem(stack, world, entity);
    }

}
