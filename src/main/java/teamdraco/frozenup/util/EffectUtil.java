package teamdraco.frozenup.util;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class EffectUtil {
    public static boolean removeEffects(LivingEntity livingEntity, Predicate<StatusEffectInstance> removalPredicate) {
        if (livingEntity.getEntityWorld().isClient()) {
            return false;
        }
        boolean removed = false;
        Set<StatusEffect> toRemove = new HashSet<>();
        for (StatusEffectInstance instance : livingEntity.getStatusEffects()) {
            if (removalPredicate.test(instance)) {
                toRemove.add(instance.getEffectType());
            }
        }
        for (StatusEffect effect : toRemove) {
            if (livingEntity.removeStatusEffect(effect)) {
                removed = true;
            }
        }
        return removed;
    }
}
