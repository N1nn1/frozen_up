package com.ninni.frozenup.mixin;

import com.ninni.frozenup.entity.ChillooEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoxEntity.class)
public abstract class FoxEntityMixin extends AnimalEntity {
    private Goal followChillooGoal;

    protected FoxEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    private void chaseDogsGoal(CallbackInfo ci) {
        this.followChillooGoal = new ActiveTargetGoal<>(this, AnimalEntity.class, 10, false, false, entity -> entity instanceof ChillooEntity);
    }

    @Inject(method = "addTypeSpecificGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 3))
    private void addFollowChillooGoal(CallbackInfo ci) {
        this.targetSelector.add(4, this.followChillooGoal);
    }
}
