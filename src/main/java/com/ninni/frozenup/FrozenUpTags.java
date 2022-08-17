package com.ninni.frozenup;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.ninni.frozenup.FrozenUp.*;

@SuppressWarnings("unused")
public interface FrozenUpTags {
    //itemTags
    TagKey<Item> CHILLOO_BREED_ITEMS = TagKey.of(Registry.ITEM_KEY, new Identifier(MOD_ID, "chilloo_breed_items"));
    TagKey<Item> MUGS = TagKey.of(Registry.ITEM_KEY, new Identifier(MOD_ID, "mugs"));
}
