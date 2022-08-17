package com.ninni.frozenup.util;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.function.Predicate;

public class Util {

    public static boolean removeEntityEffects(LivingEntity entity, Predicate<MobEffectInstance> predicate) {
        List<MobEffectInstance> mobEffectInstances = entity.getActiveEffects().stream().filter(predicate).toList();
        mobEffectInstances.forEach(mobEffectInstance -> entity.removeEffect(mobEffectInstance.getEffect()));
        return !mobEffectInstances.isEmpty();
    }

}
