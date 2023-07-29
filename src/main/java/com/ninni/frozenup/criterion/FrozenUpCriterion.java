package com.ninni.frozenup.criterion;

import com.google.gson.JsonObject;
import com.ninni.frozenup.FrozenUp;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class FrozenUpCriterion extends SimpleCriterionTrigger<FrozenUpCriterion.TriggerInstance> {
    private final ResourceLocation ID;

    public FrozenUpCriterion(String name) {
        ID = new ResourceLocation(FrozenUp.MOD_ID, name);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, conditions -> true);
    }

    @Override
    protected TriggerInstance createInstance(JsonObject jsonObject, ContextAwarePredicate predicate, DeserializationContext deserializationContext) {
        return new TriggerInstance(ID, predicate);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(ResourceLocation resourceLocation, ContextAwarePredicate composite) {
            super(resourceLocation, composite);
        }

    }
}