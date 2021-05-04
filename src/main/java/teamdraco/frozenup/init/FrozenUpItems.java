package teamdraco.frozenup.init;

import com.github.evoslab.cookiecore.datagen.MainGenerator;
import teamdraco.frozenup.FrozenUp;
import teamdraco.frozenup.item.ChocolateMilkItem;
import teamdraco.frozenup.item.HotChocolateItem;
import teamdraco.frozenup.item.MilkMugItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;

public class FrozenUpItems {
    private static final MainGenerator generator = new MainGenerator(FrozenUp.MOD_ID);

    public static Item FROZEN_TRUFFLE;
    public static Item TRUFFLE;
    public static Item TRUFFLE_MUFFIN;
    public static Item CHILLOO_SPAWN_EGG;
    public static Item CHILLOO_FEATHER;
    public static Item EMPTY_MUG;
    public static Item MUG_OF_MILK;
    public static Item MUG_OF_CHOCOLATE_MILK;
    public static Item MUG_OF_TRUFFLE_HOT_CHOCOLATE;
    
    public static Item CHILLOO_FEATHER_BLOCK;
    public static Item CHILLOO_FEATHER_BLOCK_CARPET;
    public static Item TRUFFLE_CAKE;
    public static Item CHILLOO_FEATHER_LAMP;

    public static Item registerItems(String path, Item item) {
        return generator.item.registerBlandItem(item, path);
    }

    public static void init(){
        FROZEN_TRUFFLE = FrozenUpItems.registerItems("frozen_truffle", new Item(new Item.Settings().group(FrozenUp.GROUP)));
        TRUFFLE = FrozenUpItems.registerItems("truffle", new Item(new Item.Settings().group(FrozenUp.GROUP).food(new FoodComponent.Builder().hunger(6).saturationModifier(0.5f).build())));
        TRUFFLE_MUFFIN = FrozenUpItems.registerItems("truffle_muffin", new Item(new Item.Settings().group(FrozenUp.GROUP).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).snack().build())));
        CHILLOO_SPAWN_EGG = FrozenUpItems.registerItems("chilloo_spawn_egg", new SpawnEggItem(FrozenUpEntities.CHILLOO, 0xc2cbce, 0x32383c, new Item.Settings().group(FrozenUp.GROUP)));
        CHILLOO_FEATHER = FrozenUpItems.registerItems("chilloo_feather", new Item(new Item.Settings().group(FrozenUp.GROUP)));
        EMPTY_MUG = FrozenUpItems.registerItems("empty_mug", new Item(new Item.Settings().group(FrozenUp.GROUP).maxCount(16)));
        MUG_OF_MILK = FrozenUpItems.registerItems("mug_of_milk", new MilkMugItem(new Item.Settings().group(FrozenUp.GROUP).maxCount(1)));
        MUG_OF_CHOCOLATE_MILK = FrozenUpItems.registerItems("mug_of_chocolate_milk", new ChocolateMilkItem(new Item.Settings().group(FrozenUp.GROUP).maxCount(1)));
        MUG_OF_TRUFFLE_HOT_CHOCOLATE = FrozenUpItems.registerItems("mug_of_truffle_hot_chocolate", new HotChocolateItem(new Item.Settings().group(FrozenUp.GROUP).maxCount(1)));

        CHILLOO_FEATHER_BLOCK = FrozenUpItems.registerItems("chilloo_feather_block", new BlockItem(FrozenUpBlocks.CHILLOO_FEATHER_BLOCK, new Item.Settings().group(FrozenUp.GROUP)));
        CHILLOO_FEATHER_BLOCK_CARPET = FrozenUpItems.registerItems("chilloo_feather_block_carpet", new BlockItem(FrozenUpBlocks.CHILLOO_FEATHER_BLOCK_CARPET, new Item.Settings().group(FrozenUp.GROUP)));
        TRUFFLE_CAKE = FrozenUpItems.registerItems("truffle_cake", new BlockItem(FrozenUpBlocks.TRUFFLE_CAKE, new Item.Settings().group(FrozenUp.GROUP).maxCount(1)));
        CHILLOO_FEATHER_LAMP = FrozenUpItems.registerItems("chilloo_feather_lamp", new BlockItem(FrozenUpBlocks.CHILLOO_FEATHER_LAMP, new Item.Settings().group(FrozenUp.GROUP)));
    }
}