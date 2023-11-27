package com.ninni.frozenup;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.ninni.frozenup.FrozenUp.MOD_ID;
import static com.ninni.frozenup.item.FrozenUpItems.*;

public class FrozenupCreativeItemGroup {

    static {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> entries.addAfter(Items.RED_SANDSTONE_SLAB,
                Blocks.SNOW_BLOCK,
                Blocks.SNOW,
                COMPACTED_SNOW_BRICKS,
                COMPACTED_SNOW_BRICK_STAIRS,
                COMPACTED_SNOW_BRICK_SLAB,
                COMPACTED_SNOW_FOUNDATION,
                CHILLOO_FEATHER_BLOCK,
                CHILLOO_FEATHER_COVERING,
                CHILLOO_FEATHER_LAMP,
                Blocks.ICE,
                Blocks.PACKED_ICE,
                Blocks.BLUE_ICE,
                CUT_ICE,
                CUT_ICE_STAIRS,
                CUT_ICE_SLAB,
                CUT_ICE_CUBES,
                CUT_ICE_CUBE_STAIRS,
                CUT_ICE_CUBE_SLAB,
                CUT_ICE_CUBE_WALL
        ));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.NETHER_BRICK, COMPACTED_SNOW_BRICK);
            entries.addAfter(Items.FEATHER, CHILLOO_FEATHER);
            entries.addAfter(Items.WHEAT, PINECONE, FROZEN_TRUFFLE);
            entries.addAfter(Items.FIREWORK_STAR, EMPTY_MUG);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.addAfter(Items.REDSTONE_LAMP, CHILLOO_FEATHER_LAMP);
            entries.addAfter(Items.FLOWER_POT, EMPTY_MUG);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.addAfter(Items.CHICKEN_SPAWN_EGG, CHILLOO_SPAWN_EGG);
            entries.addAfter(Items.RAVAGER_SPAWN_EGG, REINDEER_SPAWN_EGG);
            entries.addAfter(Items.PARROT_SPAWN_EGG, PENGUIN_SPAWN_EGG);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.addAfter(Items.DIAMOND_HORSE_ARMOR, HOOF_ARMOR));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.addAfter(Items.PUMPKIN_PIE, PINECONE);
            entries.addAfter(Items.CAKE, TRUFFLE_CAKE);
            entries.addAfter(Items.COOKIE, TRUFFLE_MUFFIN);
            entries.addAfter(Items.BEETROOT, TRUFFLE);
            entries.addAfter(Items.SUSPICIOUS_STEW, MUG_OF_MILK, MUG_OF_CHOCOLATE_MILK, MUG_OF_TRUFFLE_HOT_CHOCOLATE);
        });
    }


    public static final ItemGroup ITEM_GROUP = register("item_group", FabricItemGroup.builder().icon(FROZENUP::getDefaultStack).displayName(Text.translatable("spawn.item_group")).entries((featureFlagSet, output) -> {

                //spawn eggs
                output.add(CHILLOO_SPAWN_EGG);
                output.add(REINDEER_SPAWN_EGG);
                output.add(PENGUIN_SPAWN_EGG);
                //truffle related items
                output.add(FROZEN_TRUFFLE);
                output.add(TRUFFLE);
                output.add(TRUFFLE_MUFFIN);
                output.add(TRUFFLE_CAKE);
                //misc items
                output.add(PINECONE);
                output.add(HOOF_ARMOR);
                //mugs
                output.add(EMPTY_MUG);
                output.add(MUG_OF_MILK);
                output.add(MUG_OF_CHOCOLATE_MILK);
                output.add(MUG_OF_TRUFFLE_HOT_CHOCOLATE);
                //snow bricks
                output.add(COMPACTED_SNOW_BRICK);
                output.add(COMPACTED_SNOW_BRICKS);
                output.add(COMPACTED_SNOW_BRICK_STAIRS);
                output.add(COMPACTED_SNOW_BRICK_SLAB);
                output.add(COMPACTED_SNOW_FOUNDATION);
                //chilloo related items
                output.add(CHILLOO_FEATHER);
                output.add(CHILLOO_FEATHER_BLOCK);
                output.add(CHILLOO_FEATHER_COVERING);
                output.add(CHILLOO_FEATHER_LAMP);
                //ice blocks
                output.add(CUT_ICE);
                output.add(CUT_ICE_STAIRS);
                output.add(CUT_ICE_SLAB);
                output.add(CUT_ICE_CUBES);
                output.add(CUT_ICE_CUBE_STAIRS);
                output.add(CUT_ICE_CUBE_SLAB);
                output.add(CUT_ICE_CUBE_WALL);
            }).build()
    );

    private static ItemGroup register(String id, ItemGroup tab) {
        return Registry.register(Registries.ITEM_GROUP, new Identifier(FrozenUp.MOD_ID, id), tab);
    }
}
