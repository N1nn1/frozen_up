package com.ninni.frozenup.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import static com.ninni.frozenup.FrozenUp.MOD_ID;

public class FrozenUpItemTags {

    public static final TagKey<Item> CHILLOO_BREED_ITEMS = create("chilloo_breed_items");
    public static final TagKey<Item> MUGS = create("mugs");

    @NotNull
    private static TagKey<Item> create(String name) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(MOD_ID, name));
    }

}
