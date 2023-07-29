package com.ninni.frozenup.init;

import com.ninni.frozenup.FrozenUp;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class FrozenUpBiomeTags {

    public static final TagKey<Biome> PENGUIN_SPAWNS_IN = create("penguin_spawns_in");
    public static final TagKey<Biome> REINDEER_SPAWNS_IN = create("reindeer_spawns_in");
    public static final TagKey<Biome> CHILLOO = create("chilloo_spawns_in");

    private static TagKey<Biome> create(String id) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(FrozenUp.MOD_ID, id));
    }

}
