package net.teamdraco.frozenup.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AliasedBlockItem;
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
    public static final Item TRUFFLE_DIRT = register("truffle_dirt", new BlockItem(FrozenUpBlocks.TRUFFLE_DIRT, new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));
    public static final Item TRUFFLE = register("truffle", new Item(new FabricItemSettings().group(FrozenUp.ITEM_GROUP).food(new FoodComponent.Builder().hunger(6).saturationModifier(0.5F).build())));
    public static final Item TRUFFLE_MUFFIN = register("truffle_muffin", new Item(new FabricItemSettings().group(FrozenUp.ITEM_GROUP).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.4F).snack().build())));
    public static final Item TRUFFLE_CAKE = register("truffle_cake", new BlockItem(FrozenUpBlocks.TRUFFLE_CAKE, new FabricItemSettings().group(FrozenUp.ITEM_GROUP).maxCount(1)));
    //mugs
    public static final Item EMPTY_MUG = register("empty_mug", new BlockItem(FrozenUpBlocks.EMPTY_MUG, new FabricItemSettings().group(FrozenUp.ITEM_GROUP).maxCount(16)));
    public static final Item MUG_OF_MILK = register("mug_of_milk", new MilkMugItem(FrozenUpBlocks.MUG_OF_MILK, new FabricItemSettings().group(FrozenUp.ITEM_GROUP).maxCount(1)));
    public static final Item MUG_OF_CHOCOLATE_MILK = register("mug_of_chocolate_milk", new ChocolateMilkMugItem(FrozenUpBlocks.MUG_OF_CHOCOLATE_MILK, new FabricItemSettings().food(new FoodComponent.Builder().hunger(3).saturationModifier(3.0f).build()).group(FrozenUp.ITEM_GROUP).maxCount(1)));
    public static final Item MUG_OF_TRUFFLE_HOT_CHOCOLATE = register("mug_of_truffle_hot_chocolate", new TruffleHotChocolateMugItem(FrozenUpBlocks.MUG_OF_TRUFFLE_HOT_CHOCOLATE, new FabricItemSettings().group(FrozenUp.ITEM_GROUP).food(new FoodComponent.Builder().hunger(8).saturationModifier(8.0f).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 500, 0), 1.0f).build()).maxCount(1)));
    //biomes items
    public static final Item WINTER_BERRIES = register("winter_berries", new AliasedBlockItem(FrozenUpBlocks.WINTER_BERRY_BUSH, (new FabricItemSettings().group(FrozenUp.ITEM_GROUP).food(new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 6 * 20), 0.5f).hunger(4).saturationModifier(1F).build()))));
    //chilloo related items
    public static final Item CHILLOO_FEATHER = register("chilloo_feather", new Item(new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));
    public static final Item CHILLOO_FEATHER_BLOCK = register("chilloo_feather_block", new BlockItem(FrozenUpBlocks.CHILLOO_FEATHER_BLOCK, new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));
    public static final Item CHILLOO_FEATHER_BLOCK_CARPET = register("chilloo_feather_block_carpet", new BlockItem(FrozenUpBlocks.CHILLOO_FEATHER_BLOCK_CARPET, new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));
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
