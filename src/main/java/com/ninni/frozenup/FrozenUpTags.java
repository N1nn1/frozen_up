package com.ninni.frozenup;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import static com.ninni.frozenup.FrozenUp.*;

@SuppressWarnings("unused")
public interface FrozenUpTags {

    //itemTags
    TagKey<Item> CHILLOO_BREED_ITEMS = TagKey.of(Registry.ITEM_KEY, new Identifier(MOD_ID, "chilloo_breed_items"));
    TagKey<Item> MUGS = TagKey.of(Registry.ITEM_KEY, new Identifier(MOD_ID, "mugs"));
    TagKey<Item> REINDEER_TEMPTS = TagKey.of(Registry.ITEM_KEY, new Identifier(MOD_ID, "reindeer_tempts"));

    //Block tags
    TagKey<Block> PENGUIN_SPAWNABLE_ON = TagKey.of(Registry.BLOCK_KEY, new Identifier(MOD_ID, "penguin_spawnable_on"));

    //Biome tags
    TagKey<Biome> PENGUIN_SPAWNS_IN = TagKey.of(Registry.BIOME_KEY, new Identifier(MOD_ID, "penguin_spawns_in"));
    TagKey<Biome> REVAMPED_IGLOO_GENERATES = TagKey.of(Registry.BIOME_KEY, new Identifier(MOD_ID, "has_structure/revamped_igloo"));
}
