package com.ninni.frozenup.mixin;

import com.ninni.frozenup.entity.ChillooEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Fox.class)
public abstract class FoxEntityMixin extends Animal {
    private Goal followChillooGoal;

    protected FoxEntityMixin(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void chaseDogsGoal(CallbackInfo ci) {
        this.followChillooGoal = new NearestAttackableTargetGoal<>(this, Animal.class, 10, false, false, entity -> entity instanceof ChillooEntity);
    }

    @Inject(method = "setTargetGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V", ordinal = 3))
    private void addFollowChillooGoal(CallbackInfo ci) {
        this.targetSelector.addGoal(4, this.followChillooGoal);
    }
}
