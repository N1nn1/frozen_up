package teamdraco.frozenup.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import teamdraco.frozenup.FrozenUp;
import teamdraco.frozenup.item.ChocolateMilkMugItem;
import teamdraco.frozenup.item.HotChocolateMugItem;
import teamdraco.frozenup.item.MilkMugItem;

@SuppressWarnings("unused")
public class FrozenUpItems {
    public static final Item FROZEN_TRUFFLE = register("frozen_truffle", new Item(new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));
    public static final Item TRUFFLE = register("truffle", new Item(new FabricItemSettings().group(FrozenUp.ITEM_GROUP).food(new FoodComponent.Builder().hunger(6).saturationModifier(0.5F).build())));
    public static final Item TRUFFLE_MUFFIN = register("truffle_muffin", new Item(new FabricItemSettings().group(FrozenUp.ITEM_GROUP).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.4F).snack().build())));
    public static final Item CHILLOO_FEATHER = register("chilloo_feather", new Item(new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));

    public static final Item EMPTY_MUG = register("empty_mug", new BlockItem(FrozenUpBlocks.EMPTY_MUG, new Item.Settings().group(FrozenUp.ITEM_GROUP).maxCount(16)));
    public static final Item MUG_OF_MILK = register("mug_of_milk", new MilkMugItem(FrozenUpBlocks.MUG_OF_MILK, new Item.Settings().group(FrozenUp.ITEM_GROUP).maxCount(1)));
    public static final Item MUG_OF_CHOCOLATE_MILK = register("mug_of_chocolate_milk", new ChocolateMilkMugItem(FrozenUpBlocks.MUG_OF_CHOCOLATE_MILK, new Item.Settings().group(FrozenUp.ITEM_GROUP).maxCount(1)));
    public static final Item MUG_OF_TRUFFLE_HOT_CHOCOLATE = register("mug_of_truffle_hot_chocolate", new HotChocolateMugItem(FrozenUpBlocks.MUG_OF_TRUFFLE_HOT_CHOCOLATE, new Item.Settings().group(FrozenUp.ITEM_GROUP).maxCount(1)));

    public static final Item CHILLOO_FEATHER_BLOCK = register("chilloo_feather_block", new BlockItem(FrozenUpBlocks.CHILLOO_FEATHER_BLOCK, new Item.Settings().group(FrozenUp.ITEM_GROUP)));
    public static final Item CHILLOO_FEATHER_BLOCK_CARPET = register("chilloo_feather_block_carpet", new BlockItem(FrozenUpBlocks.CHILLOO_FEATHER_BLOCK_CARPET, new Item.Settings().group(FrozenUp.ITEM_GROUP)));
    public static final Item TRUFFLE_CAKE = register("truffle_cake", new BlockItem(FrozenUpBlocks.TRUFFLE_CAKE, new Item.Settings().group(FrozenUp.ITEM_GROUP).maxCount(1)));
    public static final Item CHILLOO_FEATHER_LAMP = register("chilloo_feather_lamp", new BlockItem(FrozenUpBlocks.CHILLOO_FEATHER_LAMP, new Item.Settings().group(FrozenUp.ITEM_GROUP)));

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
