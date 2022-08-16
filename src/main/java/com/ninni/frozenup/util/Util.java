package com.ninni.frozenup.util;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class Util {
    public static void removeEntityEffects(LivingEntity entity, Predicate<MobEffectInstance> predicate) {
        if (entity.level.isClientSide()) {
            Set<MobEffect> toRemove = new HashSet<>();
            for (MobEffectInstance instance : entity.getActiveEffects()) if (predicate.test(instance)) toRemove.add(instance.getEffect());

            for (MobEffect effect : toRemove) entity.removeEffect(effect);
        }
    }
}
