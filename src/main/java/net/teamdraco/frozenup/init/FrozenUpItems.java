package net.teamdraco.frozenup.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.teamdraco.frozenup.FrozenUp;
import net.teamdraco.frozenup.item.ChocolateMilkMugItem;
import net.teamdraco.frozenup.item.MilkMugItem;
import net.teamdraco.frozenup.item.TruffleHotChocolateMugItem;

@SuppressWarnings("unused")
public class FrozenUpItems {
    //truffle related items
    public static final Item FROZEN_TRUFFLE = register("frozen_truffle", new Item(new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));
    public static final Item TRUFFLE = register("truffle", new Item(new FabricItemSettings().group(FrozenUp.ITEM_GROUP).food(new FoodComponent.Builder().hunger(6).saturationModifier(0.5F).build())));
    public static final Item TRUFFLE_MUFFIN = register("truffle_muffin", new Item(new FabricItemSettings().group(FrozenUp.ITEM_GROUP).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.4F).snack().build())));
    public static final Item TRUFFLE_CAKE = register("truffle_cake", new BlockItem(FrozenUpBlocks.TRUFFLE_CAKE, new FabricItemSettings().group(FrozenUp.ITEM_GROUP).maxCount(1)));
    //mugs
    public static final Item EMPTY_MUG = register("empty_mug", new BlockItem(FrozenUpBlocks.EMPTY_MUG, new FabricItemSettings().group(FrozenUp.ITEM_GROUP).maxCount(16)));
    public static final Item MUG_OF_MILK = register("mug_of_milk", new MilkMugItem(FrozenUpBlocks.MUG_OF_MILK, new FabricItemSettings().group(FrozenUp.ITEM_GROUP).maxCount(1)));
    public static final Item MUG_OF_CHOCOLATE_MILK = register("mug_of_chocolate_milk", new ChocolateMilkMugItem(FrozenUpBlocks.MUG_OF_CHOCOLATE_MILK, new FabricItemSettings().food(new FoodComponent.Builder().hunger(3).saturationModifier(3.0f).build()).group(FrozenUp.ITEM_GROUP).maxCount(1)));
    public static final Item MUG_OF_TRUFFLE_HOT_CHOCOLATE = register("mug_of_truffle_hot_chocolate", new TruffleHotChocolateMugItem(FrozenUpBlocks.MUG_OF_TRUFFLE_HOT_CHOCOLATE, new FabricItemSettings().group(FrozenUp.ITEM_GROUP).food(new FoodComponent.Builder().hunger(8).saturationModifier(8.0f).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 500, 0), 1.0f).build()).maxCount(1)));
    //ice blocks
    public static final Item CUT_ICE = register("cut_ice", new BlockItem(FrozenUpBlocks.CUT_ICE, new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));
    public static final Item CUT_ICE_STAIRS = register("cut_ice_stairs", new BlockItem(FrozenUpBlocks.CUT_ICE_STAIRS, new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));
    public static final Item CUT_ICE_SLAB = register("cut_ice_slab", new BlockItem(FrozenUpBlocks.CUT_ICE_SLAB, new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));
    public static final Item CUT_ICE_CUBES = register("cut_ice_cubes", new BlockItem(FrozenUpBlocks.CUT_ICE_CUBES, new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));
    public static final Item CUT_ICE_CUBE_STAIRS = register("cut_ice_cube_stairs", new BlockItem(FrozenUpBlocks.CUT_ICE_CUBE_STAIRS, new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));
    public static final Item CUT_ICE_CUBE_SLAB = register("cut_ice_cube_slab", new BlockItem(FrozenUpBlocks.CUT_ICE_CUBE_SLAB, new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));
    public static final Item CUT_ICE_CUBE_WALL = register("cut_ice_cube_wall", new BlockItem(FrozenUpBlocks.CUT_ICE_CUBE_WALL, new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));

    //chilloo related items
    public static final Item CHILLOO_FEATHER = register("chilloo_feather", new Item(new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));
    public static final Item CHILLOO_FEATHER_BLOCK = register("chilloo_feather_block", new BlockItem(FrozenUpBlocks.CHILLOO_FEATHER_BLOCK, new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));
    public static final Item CHILLOO_FEATHER_COVERING = register("chilloo_feather_covering", new BlockItem(FrozenUpBlocks.CHILLOO_FEATHER_COVERING, new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));
    public static final Item CHILLOO_FEATHER_LAMP = register("chilloo_feather_lamp", new BlockItem(FrozenUpBlocks.CHILLOO_FEATHER_LAMP, new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));


    static {
        CompostingChanceRegistry composting = CompostingChanceRegistry.INSTANCE;
        composting.add(TRUFFLE, 0.3F);
        composting.add(TRUFFLE_MUFFIN, 0.3F);
        composting.add(TRUFFLE_CAKE, 0.5F);
    }

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(FrozenUp.MOD_ID, id), item);
    }
}
