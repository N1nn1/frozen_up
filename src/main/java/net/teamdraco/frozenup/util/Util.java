package net.teamdraco.frozenup.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class Util {
    public static void removeEntityEffects(LivingEntity entity, Predicate<StatusEffectInstance> predicate) {
        if (entity.world.isClient()) {
            Set<StatusEffect> toRemove = new HashSet<>();
            for (StatusEffectInstance instance : entity.getStatusEffects()) {
                if (predicate.test(instance)) {
                    toRemove.add(instance.getEffectType());
                }
            }

            for (StatusEffect effect : toRemove) {
                entity.removeStatusEffect(effect);
            }
        }
    }
}
