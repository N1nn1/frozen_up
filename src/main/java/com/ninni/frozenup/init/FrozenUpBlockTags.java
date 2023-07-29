package com.ninni.frozenup.init;

import com.ninni.frozenup.FrozenUp;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class FrozenUpBlockTags {

    public static final TagKey<Block> PENGUIN_SPAWNABLE_ON = create("penguin_spawnable_on");

    private static TagKey<Block> create(String id) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(FrozenUp.MOD_ID, id));
    }

}
