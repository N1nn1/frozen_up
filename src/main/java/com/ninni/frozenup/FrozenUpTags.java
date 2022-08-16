package com.ninni.frozenup;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.ninni.frozenup.FrozenUp.MOD_ID;

@SuppressWarnings("unused")
public interface FrozenUpTags {
    //itemTags
    TagKey<Item> CHILLOO_BREED_ITEMS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(MOD_ID, "chilloo_breed_items"));
}
