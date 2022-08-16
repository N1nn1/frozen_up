package com.ninni.frozenup.init;

import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.item.ChocolateMilkMugItem;
import com.ninni.frozenup.item.MilkMugItem;
import com.ninni.frozenup.item.TruffleHotChocolateMugItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = FrozenUp.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrozenUpItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FrozenUp.MOD_ID);

    //logo
    public static final RegistryObject<Item> FROZENUP = ITEMS.register("frozenup", () -> new Item(new Item.Properties().fireResistant().rarity(Rarity.EPIC).stacksTo(1)));
    //truffle related items
    public static final RegistryObject<Item> FROZEN_TRUFFLE = ITEMS.register("frozen_truffle", () -> new Item(new Item.Properties().tab(FrozenUp.ITEM_GROUP)));
    public static final RegistryObject<Item> TRUFFLE = ITEMS.register("truffle", () -> new Item(new Item.Properties().tab(FrozenUp.ITEM_GROUP).food(new FoodProperties.Builder().nutrition(6).saturationMod(0.5F).build())));
    public static final RegistryObject<Item> TRUFFLE_MUFFIN = ITEMS.register("truffle_muffin", () -> new Item(new Item.Properties().tab(FrozenUp.ITEM_GROUP).food(new FoodProperties.Builder().nutrition(4).saturationMod(0.4F).fast().build())));
    public static final RegistryObject<Item> TRUFFLE_CAKE = ITEMS.register("truffle_cake", () -> new BlockItem(FrozenUpBlocks.TRUFFLE_CAKE.get(), new Item.Properties().tab(FrozenUp.ITEM_GROUP).stacksTo(1)));
    //mugs
    public static final RegistryObject<Item> EMPTY_MUG = ITEMS.register("empty_mug", () -> new BlockItem(FrozenUpBlocks.EMPTY_MUG.get(), new Item.Properties().tab(FrozenUp.ITEM_GROUP).stacksTo(16)));
    public static final RegistryObject<Item> MUG_OF_MILK = ITEMS.register("mug_of_milk", () -> new MilkMugItem(FrozenUpBlocks.MUG_OF_MILK.get(), new Item.Properties().tab(FrozenUp.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> MUG_OF_CHOCOLATE_MILK = ITEMS.register("mug_of_chocolate_milk", () -> new ChocolateMilkMugItem(FrozenUpBlocks.MUG_OF_CHOCOLATE_MILK.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(3.0f).build()).tab(FrozenUp.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> MUG_OF_TRUFFLE_HOT_CHOCOLATE = ITEMS.register("mug_of_truffle_hot_chocolate", () -> new TruffleHotChocolateMugItem(FrozenUpBlocks.MUG_OF_TRUFFLE_HOT_CHOCOLATE.get(), new Item.Properties().tab(FrozenUp.ITEM_GROUP).food(new FoodProperties.Builder().nutrition(8).saturationMod(8.0f).build()).stacksTo(1)));
    //ice blocks
    public static final RegistryObject<Item> CUT_ICE = ITEMS.register("cut_ice", () -> new BlockItem(FrozenUpBlocks.CUT_ICE.get(), new Item.Properties().tab(FrozenUp.ITEM_GROUP)));
    public static final RegistryObject<Item> CUT_ICE_STAIRS = ITEMS.register("cut_ice_stairs", () -> new BlockItem(FrozenUpBlocks.CUT_ICE_STAIRS.get(), new Item.Properties().tab(FrozenUp.ITEM_GROUP)));
    public static final RegistryObject<Item> CUT_ICE_SLAB = ITEMS.register("cut_ice_slab", () -> new BlockItem(FrozenUpBlocks.CUT_ICE_SLAB.get(), new Item.Properties().tab(FrozenUp.ITEM_GROUP)));
    public static final RegistryObject<Item> CUT_ICE_CUBES = ITEMS.register("cut_ice_cubes", () -> new BlockItem(FrozenUpBlocks.CUT_ICE_CUBES.get(), new Item.Properties().tab(FrozenUp.ITEM_GROUP)));
    public static final RegistryObject<Item> CUT_ICE_CUBE_STAIRS = ITEMS.register("cut_ice_cube_stairs", () -> new BlockItem(FrozenUpBlocks.CUT_ICE_CUBE_STAIRS.get(), new Item.Properties().tab(FrozenUp.ITEM_GROUP)));
    public static final RegistryObject<Item> CUT_ICE_CUBE_SLAB = ITEMS.register("cut_ice_cube_slab", () -> new BlockItem(FrozenUpBlocks.CUT_ICE_CUBE_SLAB.get(), new Item.Properties().tab(FrozenUp.ITEM_GROUP)));
    public static final RegistryObject<Item> CUT_ICE_CUBE_WALL = ITEMS.register("cut_ice_cube_wall", () -> new BlockItem(FrozenUpBlocks.CUT_ICE_CUBE_WALL.get(), new Item.Properties().tab(FrozenUp.ITEM_GROUP)));

    //chilloo related items
    public static final RegistryObject<Item> CHILLOO_FEATHER = ITEMS.register("chilloo_feather", () -> new Item(new Item.Properties().tab(FrozenUp.ITEM_GROUP)));
    public static final RegistryObject<Item> CHILLOO_FEATHER_BLOCK = ITEMS.register("chilloo_feather_block", () -> new BlockItem(FrozenUpBlocks.CHILLOO_FEATHER_BLOCK.get(), new Item.Properties().tab(FrozenUp.ITEM_GROUP)));
    public static final RegistryObject<Item> CHILLOO_FEATHER_COVERING = ITEMS.register("chilloo_feather_covering", () -> new BlockItem(FrozenUpBlocks.CHILLOO_FEATHER_COVERING.get(), new Item.Properties().tab(FrozenUp.ITEM_GROUP)));
    public static final RegistryObject<Item> CHILLOO_FEATHER_LAMP = ITEMS.register("chilloo_feather_lamp", () -> new BlockItem(FrozenUpBlocks.CHILLOO_FEATHER_LAMP.get(), new Item.Properties().tab(FrozenUp.ITEM_GROUP)));

    public static final RegistryObject<Item> CHILLOO_SPAWN_EGG = ITEMS.register("chilloo_spawn_egg", () -> new ForgeSpawnEggItem(FrozenUpEntities.CHILLOO, 0xffffff, 0x32383c, new Item.Properties().tab(FrozenUp.ITEM_GROUP)));

}
