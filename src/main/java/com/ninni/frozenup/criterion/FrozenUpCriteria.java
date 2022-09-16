package com.ninni.frozenup.criterion;

import net.minecraft.advancement.criterion.Criteria;

public class FrozenUpCriteria {
    public static FrozenUpCriterion CLOUD_JUMPER_BOOST;
    public static FrozenUpCriterion TAME_A_CHILLOO;
    public static FrozenUpCriterion RETRIEVE_ITEM_FROM_TAMED_CHILLOO;
    public static FrozenUpCriterion CURE_HARMFUL_STATUS_EFFECTS;

    public static void init() {
        TAME_A_CHILLOO = Criteria.register(new FrozenUpCriterion("tame_a_chilloo"));
        RETRIEVE_ITEM_FROM_TAMED_CHILLOO = Criteria.register(new FrozenUpCriterion("retrieve_item_from_tamed_chilloo"));
        CURE_HARMFUL_STATUS_EFFECTS = Criteria.register(new FrozenUpCriterion("cure_harmful_status_effects"));
        CLOUD_JUMPER_BOOST = Criteria.register(new FrozenUpCriterion("cloud_jumper_boost"));
    }
}
