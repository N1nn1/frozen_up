package com.ninni.frozenup.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static com.ninni.frozenup.FrozenUp.*;

public class FrozenUpCriterion extends AbstractCriterion<FrozenUpCriterion.TriggerInstance> {
    private final Identifier ID;

    public FrozenUpCriterion(String name) {
        ID = new Identifier(MOD_ID, name);
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player) {
        this.trigger(player, conditions -> true);
    }

    @Override
    protected TriggerInstance conditionsFromJson(JsonObject obj, LootContextPredicate playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new FrozenUpCriterion.TriggerInstance(ID, playerPredicate);
    }

    public static class TriggerInstance extends AbstractCriterionConditions {

        public TriggerInstance(Identifier id, LootContextPredicate playerPredicate) {
            super(id, playerPredicate);
        }

    }
}
