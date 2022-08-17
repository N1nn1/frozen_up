package com.ninni.frozenup;

import com.google.common.reflect.Reflection;
import com.ninni.frozenup.block.FrozenUpBlocks;
import com.ninni.frozenup.criterion.FrozenUpCriteria;
import com.ninni.frozenup.item.FrozenUpItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import com.ninni.frozenup.entity.FrozenUpEntities;

public class FrozenUp implements ModInitializer {
    public static final String MOD_ID = "frozenup";
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
        new Identifier(FrozenUp.MOD_ID, "item_group"),
        () -> new ItemStack(FrozenUpItems.FROZENUP)
    );

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        FrozenUpCriteria.init();
        Reflection.initialize(
            FrozenUpBlocks.class,
            FrozenUpItems.class,
            FrozenUpEntities.class
        );
    }
}
