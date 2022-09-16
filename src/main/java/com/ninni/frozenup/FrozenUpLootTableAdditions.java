package com.ninni.frozenup;

import com.ninni.frozenup.enchantments.FrozenUpEnchantments;
import com.ninni.frozenup.item.FrozenUpItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.AlternativeLootCondition;
import net.minecraft.loot.condition.InvertedLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;

public class FrozenUpLootTableAdditions {
    private static final float[] PINECONE_DROP_CHANCE = new float[]{0.05F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F};
    private static final LootCondition.Builder WITH_SILK_TOUCH = MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))));
    private static final LootCondition.Builder WITH_SHEARS = MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(Items.SHEARS));

    static {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.equals(Blocks.SPRUCE_LEAVES.getLootTableId())) {
                tableBuilder.pool(LootPool.builder()
                                          .with(ItemEntry.builder(FrozenUpItems.PINECONE)
                                                         .conditionally(SurvivesExplosionLootCondition.builder())
                                                         .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, PINECONE_DROP_CHANCE)))
                                          .conditionally(InvertedLootCondition.builder(AlternativeLootCondition.builder(WITH_SHEARS, WITH_SILK_TOUCH))));
            }
            if (id.equals(LootTables.IGLOO_CHEST_CHEST)) {
                tableBuilder.pool(LootPool.builder()
                                          .with(ItemEntry.builder(Items.BOOK)
                                                         .weight(10)
                                                         .apply(EnchantRandomlyLootFunction.create().add(FrozenUpEnchantments.CLOUD_JUMPER).add(FrozenUpEnchantments.HASTY_HOOVES)))
                                          .with(ItemEntry.builder(FrozenUpItems.COMPACTED_SNOW_BRICK)
                                                         .weight(3)
                                                         .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 16))))
                                          .with(ItemEntry.builder(Items.SADDLE)
                                                         .weight(2)
                                                         .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 1))))
                                          .with(ItemEntry.builder(Items.GLOW_LICHEN)
                                                         .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 8))))

                );
            }
            if (id.equals(LootTables.VILLAGE_SNOWY_HOUSE_CHEST)) {
                tableBuilder.pool(LootPool.builder()
                                          .with(ItemEntry.builder(FrozenUpItems.COMPACTED_SNOW_BRICK)
                                                         .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 4))))
                                          .with(ItemEntry.builder(FrozenUpItems.PINECONE)
                                                         .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 1))))

                );
            }
            if (id.equals(LootTables.VILLAGE_TAIGA_HOUSE_CHEST)) {
                tableBuilder.pool(LootPool.builder()
                                          .with(ItemEntry.builder(FrozenUpItems.PINECONE)
                                                         .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 3))))

                );
            }
        });
    }

}
