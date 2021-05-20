package teamdraco.frozenup.init;

import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import teamdraco.frozenup.FrozenUp;
import teamdraco.frozenup.item.ChocolateMilkMugItem;
import teamdraco.frozenup.item.HotChocolateMugItem;
import teamdraco.frozenup.item.MilkMugItem;

public class FrozenUpItems {
    public static Item FROZEN_TRUFFLE;
    public static Item TRUFFLE;
    public static Item TRUFFLE_MUFFIN;
    public static SpawnEggItem CHILLOO_SPAWN_EGG;
    public static Item CHILLOO_FEATHER;

    public static BlockItem EMPTY_MUG;
    public static MilkMugItem MUG_OF_MILK;
    public static ChocolateMilkMugItem MUG_OF_CHOCOLATE_MILK;
    public static HotChocolateMugItem MUG_OF_TRUFFLE_HOT_CHOCOLATE;
    
    public static BlockItem CHILLOO_FEATHER_BLOCK;
    public static BlockItem CHILLOO_FEATHER_BLOCK_CARPET;
    public static BlockItem TRUFFLE_CAKE;
    public static BlockItem CHILLOO_FEATHER_LAMP;

    public static void init() {
        FROZEN_TRUFFLE = registerItem("frozen_truffle", new Item(new Item.Settings().group(FrozenUp.GROUP)));
        TRUFFLE = registerItem("truffle", new Item(new Item.Settings().group(FrozenUp.GROUP).food(new FoodComponent.Builder().hunger(6).saturationModifier(0.5f).build())));
        TRUFFLE_MUFFIN = registerItem("truffle_muffin", new Item(new Item.Settings().group(FrozenUp.GROUP).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).snack().build())));
        CHILLOO_SPAWN_EGG = registerItem("chilloo_spawn_egg", new SpawnEggItem(FrozenUpEntities.CHILLOO, 0xc2cbce, 0x32383c, new Item.Settings().group(FrozenUp.GROUP)));
        CHILLOO_FEATHER = registerItem("chilloo_feather", new Item(new Item.Settings().group(FrozenUp.GROUP)));

        EMPTY_MUG = registerItem("empty_mug", new BlockItem(FrozenUpBlocks.EMPTY_MUG, new Item.Settings().group(FrozenUp.GROUP).maxCount(16)));
        MUG_OF_MILK = registerItem("mug_of_milk", new MilkMugItem(FrozenUpBlocks.MUG_OF_MILK, new Item.Settings().group(FrozenUp.GROUP).maxCount(1)));
        MUG_OF_CHOCOLATE_MILK = registerItem("mug_of_chocolate_milk", new ChocolateMilkMugItem(FrozenUpBlocks.MUG_OF_CHOCOLATE_MILK, new Item.Settings().group(FrozenUp.GROUP).maxCount(1)));
        MUG_OF_TRUFFLE_HOT_CHOCOLATE = registerItem("mug_of_truffle_hot_chocolate", new HotChocolateMugItem(FrozenUpBlocks.MUG_OF_TRUFFLE_HOT_CHOCOLATE, new Item.Settings().group(FrozenUp.GROUP).maxCount(1)));

        CHILLOO_FEATHER_BLOCK = registerItem("chilloo_feather_block", new BlockItem(FrozenUpBlocks.CHILLOO_FEATHER_BLOCK, new Item.Settings().group(FrozenUp.GROUP)));
        CHILLOO_FEATHER_BLOCK_CARPET = registerItem("chilloo_feather_block_carpet", new BlockItem(FrozenUpBlocks.CHILLOO_FEATHER_BLOCK_CARPET, new Item.Settings().group(FrozenUp.GROUP)));
        TRUFFLE_CAKE = registerItem("truffle_cake", new BlockItem(FrozenUpBlocks.TRUFFLE_CAKE, new Item.Settings().group(FrozenUp.GROUP).maxCount(1)));
        CHILLOO_FEATHER_LAMP = registerItem("chilloo_feather_lamp", new BlockItem(FrozenUpBlocks.CHILLOO_FEATHER_LAMP, new Item.Settings().group(FrozenUp.GROUP)));
    }

    public static <T extends Item> T registerItem(String path, T item) {
        return FrozenUp.GENERATOR.item.registerBlandItem(item, path);
    }
}
