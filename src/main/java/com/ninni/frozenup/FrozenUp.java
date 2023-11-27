package com.ninni.frozenup;

import com.google.common.reflect.Reflection;
import com.ninni.frozenup.advancements.FrozenUpCriteriaTriggers;
import com.ninni.frozenup.block.FrozenUpBlocks;
import com.ninni.frozenup.enchantments.FrozenUpEnchantments;
import com.ninni.frozenup.entity.FrozenUpEntities;
import com.ninni.frozenup.item.FrozenUpItems;
import com.ninni.frozenup.sound.FrozenUpSoundEvents;
import net.fabricmc.api.ModInitializer;

public class FrozenUp implements ModInitializer {
    public static final String MOD_ID = "frozenup";

    @Override
    public void onInitialize() {
        Reflection.initialize(
            FrozenUpBlocks.class,
            FrozenupCreativeItemGroup.class,
            FrozenUpSoundEvents.class,
            FrozenUpCriteriaTriggers.class,
            FrozenUpLootTableAdditions.class,
            FrozenUpEnchantments.class,
            FrozenUpItems.class,
            FrozenUpEntities.class
        );
    }
}
