package com.ninni.frozenup.init;

import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.criterion.FrozenUpCriterion;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FrozenUp.MOD_ID)
public class FrozenUpCriteriaTriggers {

    public static final FrozenUpCriterion TAME_A_CHILLOO = CriteriaTriggers.register(new FrozenUpCriterion("tame_a_chilloo"));
    public static final FrozenUpCriterion RETRIEVE_ITEM_FROM_TAMED_CHILLOO = CriteriaTriggers.register(new FrozenUpCriterion("retrieve_item_from_tamed_chilloo"));
    public static final FrozenUpCriterion CURE_HARMFUL_STATUS_EFFECTS = CriteriaTriggers.register(new FrozenUpCriterion("cure_harmful_status_effects"));
    public static final FrozenUpCriterion CLOUD_JUMPER_BOOST = CriteriaTriggers.register(new FrozenUpCriterion("cloud_jumper_boost"));

}
