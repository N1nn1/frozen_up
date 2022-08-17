package com.ninni.frozenup.criterion;

import net.minecraft.advancement.criterion.Criteria;

public class FrozenUpCriteria {
    public static FrozenUpCriterion TAME_A_CHILLOO;
    public static FrozenUpCriterion RETRIEVE_ITEM_FROM_TAMED_CHILLOO;

    public static void init() {
        TAME_A_CHILLOO = Criteria.register(new FrozenUpCriterion("tame_a_chilloo"));
        RETRIEVE_ITEM_FROM_TAMED_CHILLOO = Criteria.register(new FrozenUpCriterion("retrieve_item_from_tamed_chilloo"));
    }
}
