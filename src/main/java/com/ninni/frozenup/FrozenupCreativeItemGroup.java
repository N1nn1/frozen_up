package com.ninni.frozenup;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.ninni.frozenup.FrozenUp.MOD_ID;
import static com.ninni.frozenup.item.FrozenUpItems.CHILLOO_FEATHER;
import static com.ninni.frozenup.item.FrozenUpItems.CHILLOO_FEATHER_BLOCK;
import static com.ninni.frozenup.item.FrozenUpItems.CHILLOO_FEATHER_COVERING;
import static com.ninni.frozenup.item.FrozenUpItems.CHILLOO_FEATHER_LAMP;
import static com.ninni.frozenup.item.FrozenUpItems.CHILLOO_SPAWN_EGG;
import static com.ninni.frozenup.item.FrozenUpItems.COMPACTED_SNOW_BRICK;
import static com.ninni.frozenup.item.FrozenUpItems.COMPACTED_SNOW_BRICKS;
import static com.ninni.frozenup.item.FrozenUpItems.COMPACTED_SNOW_BRICK_SLAB;
import static com.ninni.frozenup.item.FrozenUpItems.COMPACTED_SNOW_BRICK_STAIRS;
import static com.ninni.frozenup.item.FrozenUpItems.COMPACTED_SNOW_FOUNDATION;
import static com.ninni.frozenup.item.FrozenUpItems.CUT_ICE;
import static com.ninni.frozenup.item.FrozenUpItems.CUT_ICE_CUBES;
import static com.ninni.frozenup.item.FrozenUpItems.CUT_ICE_CUBE_SLAB;
import static com.ninni.frozenup.item.FrozenUpItems.CUT_ICE_CUBE_STAIRS;
import static com.ninni.frozenup.item.FrozenUpItems.CUT_ICE_CUBE_WALL;
import static com.ninni.frozenup.item.FrozenUpItems.CUT_ICE_SLAB;
import static com.ninni.frozenup.item.FrozenUpItems.CUT_ICE_STAIRS;
import static com.ninni.frozenup.item.FrozenUpItems.EMPTY_MUG;
import static com.ninni.frozenup.item.FrozenUpItems.FROZENUP;
import static com.ninni.frozenup.item.FrozenUpItems.FROZEN_TRUFFLE;
import static com.ninni.frozenup.item.FrozenUpItems.HOOF_ARMOR;
import static com.ninni.frozenup.item.FrozenUpItems.MUG_OF_CHOCOLATE_MILK;
import static com.ninni.frozenup.item.FrozenUpItems.MUG_OF_MILK;
import static com.ninni.frozenup.item.FrozenUpItems.MUG_OF_TRUFFLE_HOT_CHOCOLATE;
import static com.ninni.frozenup.item.FrozenUpItems.PENGUIN_SPAWN_EGG;
import static com.ninni.frozenup.item.FrozenUpItems.PINECONE;
import static com.ninni.frozenup.item.FrozenUpItems.REINDEER_SPAWN_EGG;
import static com.ninni.frozenup.item.FrozenUpItems.TRUFFLE;
import static com.ninni.frozenup.item.FrozenUpItems.TRUFFLE_CAKE;
import static com.ninni.frozenup.item.FrozenUpItems.TRUFFLE_MUFFIN;

public class FrozenupCreativeItemGroup {

    public static final ItemGroup FROZEN_UP = Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "item_group"), FabricItemGroup.builder()
            .icon(FROZENUP::getDefaultStack)
            .displayName(Text.translatable("itemGroup.frozenup.item_group"))
            .entries((displayContext, entries) -> {
                //spawn eggs
                entries.add(CHILLOO_SPAWN_EGG);
                entries.add(REINDEER_SPAWN_EGG);
                entries.add(PENGUIN_SPAWN_EGG);
                //truffle related items
                entries.add(FROZEN_TRUFFLE);
                entries.add(TRUFFLE);
                entries.add(TRUFFLE_MUFFIN);
                entries.add(TRUFFLE_CAKE);
                //misc items
                entries.add(PINECONE);
                entries.add(HOOF_ARMOR);
                //mugs
                entries.add(EMPTY_MUG);
                entries.add(MUG_OF_MILK);
                entries.add(MUG_OF_CHOCOLATE_MILK);
                entries.add(MUG_OF_TRUFFLE_HOT_CHOCOLATE);
                //snow bricks
                entries.add(COMPACTED_SNOW_BRICK);
                entries.add(COMPACTED_SNOW_BRICKS);
                entries.add(COMPACTED_SNOW_BRICK_STAIRS);
                entries.add(COMPACTED_SNOW_BRICK_SLAB);
                entries.add(COMPACTED_SNOW_FOUNDATION);
                //chilloo related items
                entries.add(CHILLOO_FEATHER);
                entries.add(CHILLOO_FEATHER_BLOCK);
                entries.add(CHILLOO_FEATHER_COVERING);
                entries.add(CHILLOO_FEATHER_LAMP);
                //ice blocks
                entries.add(CUT_ICE);
                entries.add(CUT_ICE_STAIRS);
                entries.add(CUT_ICE_SLAB);
                entries.add(CUT_ICE_CUBES);
                entries.add(CUT_ICE_CUBE_STAIRS);
                entries.add(CUT_ICE_CUBE_SLAB);
                entries.add(CUT_ICE_CUBE_WALL);
            }).build());

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
}
