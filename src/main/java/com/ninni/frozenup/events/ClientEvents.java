package com.ninni.frozenup.events;

import com.google.common.collect.Lists;
import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.client.client.init.FrozenUpEntityModelLayers;
import com.ninni.frozenup.client.model.ChillooEntityModel;
import com.ninni.frozenup.client.model.PenguinEntityModel;
import com.ninni.frozenup.client.model.ReindeerEntityModel;
import com.ninni.frozenup.client.renderer.ChillooEntityRenderer;
import com.ninni.frozenup.client.renderer.PenguinEntityRenderer;
import com.ninni.frozenup.client.renderer.ReindeerEntityRenderer;
import com.ninni.frozenup.init.FrozenUpEntities;
import com.ninni.frozenup.init.FrozenUpItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collections;

@Mod.EventBusSubscriber(modid = FrozenUp.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void buildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> key = event.getTabKey();
        MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries = event.getEntries();
        if (key.equals(CreativeModeTabs.BUILDING_BLOCKS)) {
            putAfter(entries, Items.RED_SANDSTONE_SLAB, Blocks.SNOW_BLOCK, Blocks.SNOW, FrozenUpItems.COMPACTED_SNOW_BRICKS.get(), FrozenUpItems.COMPACTED_SNOW_BRICK_STAIRS.get(), FrozenUpItems.COMPACTED_SNOW_BRICK_SLAB.get(), FrozenUpItems.COMPACTED_SNOW_FOUNDATION.get(), FrozenUpItems.CHILLOO_FEATHER_BLOCK.get(), FrozenUpItems.CHILLOO_FEATHER_COVERING.get(), FrozenUpItems.CHILLOO_FEATHER_LAMP.get(), Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE, FrozenUpItems.CUT_ICE.get(), FrozenUpItems.CUT_ICE_STAIRS.get(), FrozenUpItems.CUT_ICE_SLAB.get(), FrozenUpItems.CUT_ICE_CUBES.get(), FrozenUpItems.CUT_ICE_CUBE_STAIRS.get(), FrozenUpItems.CUT_ICE_CUBE_SLAB.get(), FrozenUpItems.CUT_ICE_CUBE_WALL.get());
        }
        if (key.equals(CreativeModeTabs.INGREDIENTS)) {
            putAfter(entries, Items.NETHER_BRICK, FrozenUpItems.COMPACTED_SNOW_BRICK.get());
            putAfter(entries, Items.FEATHER, FrozenUpItems.CHILLOO_FEATHER.get());
            putAfter(entries, Items.WHEAT, FrozenUpItems.PINECONE.get(), FrozenUpItems.FROZEN_TRUFFLE.get());
            putAfter(entries, Items.FIREWORK_STAR, FrozenUpItems.EMPTY_MUG.get());
        }
        if (key.equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
            putAfter(entries, Items.REDSTONE_LAMP, FrozenUpItems.CHILLOO_FEATHER_LAMP.get());
            putAfter(entries, Items.FLOWER_POT, FrozenUpItems.EMPTY_MUG.get());
        }
        if (key.equals(CreativeModeTabs.SPAWN_EGGS)) {
            putAfter(entries, Items.CHICKEN_SPAWN_EGG, FrozenUpItems.CHILLOO_SPAWN_EGG.get());
            putAfter(entries, Items.RAVAGER_SPAWN_EGG, FrozenUpItems.REINDEER_SPAWN_EGG.get());
            putAfter(entries, Items.PARROT_SPAWN_EGG, FrozenUpItems.PENGUIN_SPAWN_EGG.get());
        }
        if (key.equals(CreativeModeTabs.COMBAT)) {
            putAfter(entries, Items.DIAMOND_HORSE_ARMOR, FrozenUpItems.HOOF_ARMOR.get());
        }
        if (key.equals(CreativeModeTabs.FOOD_AND_DRINKS)) {
            putAfter(entries, Items.PUMPKIN_PIE, FrozenUpItems.PINECONE.get());
            putAfter(entries, Items.CAKE, FrozenUpItems.TRUFFLE_CAKE.get());
            putAfter(entries, Items.COOKIE, FrozenUpItems.TRUFFLE_MUFFIN.get());
            putAfter(entries, Items.BEETROOT, FrozenUpItems.TRUFFLE.get());
            putAfter(entries, Items.SUSPICIOUS_STEW, FrozenUpItems.MUG_OF_MILK.get(), FrozenUpItems.MUG_OF_CHOCOLATE_MILK.get(), FrozenUpItems.MUG_OF_TRUFFLE_HOT_CHOCOLATE.get());
        }
    }

    private static void putAfter(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries, ItemLike item, ItemLike... insertion) {
        ArrayList<ItemLike> list = Lists.newArrayList(insertion);
        Collections.reverse(list);
        list.forEach(itemLike -> {
            entries.putAfter(new ItemStack(item), new ItemStack(itemLike), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        });
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(FrozenUpEntities.CHILLOO.get(), ChillooEntityRenderer::new);
        event.registerEntityRenderer(FrozenUpEntities.REINDEER.get(), ReindeerEntityRenderer::new);
        event.registerEntityRenderer(FrozenUpEntities.PENGUIN.get(), PenguinEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FrozenUpEntityModelLayers.CHILLOO, ChillooEntityModel::getLayerDefinition);
        event.registerLayerDefinition(FrozenUpEntityModelLayers.REINDEER, ReindeerEntityModel::getLayerDefinition);
        event.registerLayerDefinition(FrozenUpEntityModelLayers.REINDEER_ARMOR, ReindeerEntityModel::getLayerDefinition);
        event.registerLayerDefinition(FrozenUpEntityModelLayers.PENGUIN, PenguinEntityModel::getLayerDefinition);
    }

}
