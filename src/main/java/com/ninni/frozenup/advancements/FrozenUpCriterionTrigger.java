package com.ninni.frozenup.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static com.ninni.frozenup.FrozenUp.MOD_ID;

public class FrozenUpCriterionTrigger extends AbstractCriterion<FrozenUpCriterionTrigger.TriggerInstance> {
    private final Identifier ID;

    public FrozenUpCriterionTrigger(String id) {
        ID = new Identifier(MOD_ID, id);
    }

    public void trigger(ServerPlayerEntity player) {
        this.trigger(player, conditions -> true);
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    protected TriggerInstance conditionsFromJson(JsonObject obj, LootContextPredicate playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new TriggerInstance(ID, playerPredicate);
    }

    public static class TriggerInstance extends AbstractCriterionConditions {

        public TriggerInstance(Identifier identifier, LootContextPredicate predicate) {
            super(identifier, predicate);
        }
    }
}