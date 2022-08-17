package com.ninni.frozenup.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class Util {
    public static boolean removeEntityEffects(LivingEntity entity, Predicate<StatusEffect> predicate) {
        List<StatusEffect> list = entity.getActiveStatusEffects()
                                        .keySet()
                                        .stream()
                                        .filter(predicate)
                                        .toList();
        list.forEach(entity::removeStatusEffect);
        return !list.isEmpty();
    }
}
