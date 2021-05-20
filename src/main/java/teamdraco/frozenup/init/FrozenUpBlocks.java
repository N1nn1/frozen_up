package teamdraco.frozenup.init;

import java.util.function.ToIntFunction;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import teamdraco.frozenup.FrozenUp;
import teamdraco.frozenup.block.CarpetBlock;
import teamdraco.frozenup.block.FeatherLampBlock;
import teamdraco.frozenup.block.MugBlock;

public class FrozenUpBlocks {
    public static Block CHILLOO_FEATHER_BLOCK;
    public static CarpetBlock CHILLOO_FEATHER_BLOCK_CARPET;
    public static CakeBlock TRUFFLE_CAKE;
    public static FeatherLampBlock CHILLOO_FEATHER_LAMP;
    public static MugBlock EMPTY_MUG;
    public static MugBlock MUG_OF_MILK;
    public static MugBlock MUG_OF_CHOCOLATE_MILK;
    public static MugBlock MUG_OF_TRUFFLE_HOT_CHOCOLATE;

    public static void init() {
        CHILLOO_FEATHER_BLOCK = registerBlock("chilloo_feather_block", new Block(AbstractBlock.Settings.of(Material.SOLID_ORGANIC).sounds(BlockSoundGroup.SNOW).strength(0.1f)));
        CHILLOO_FEATHER_BLOCK_CARPET = registerBlock("chilloo_feather_block_carpet", new CarpetBlock(AbstractBlock.Settings.of(Material.SOLID_ORGANIC).sounds(BlockSoundGroup.SNOW).strength(0.1f)));
        TRUFFLE_CAKE = registerBlock("truffle_cake", new CakeBlock(AbstractBlock.Settings.of(Material.CAKE).strength(0.5F).sounds(BlockSoundGroup.WOOL)));
        CHILLOO_FEATHER_LAMP = registerBlock("chilloo_feather_lamp", new FeatherLampBlock(AbstractBlock.Settings.of(Material.SNOW_BLOCK).luminance(getLightValueLit(10)).sounds(BlockSoundGroup.SNOW).strength(0.3F)));
        EMPTY_MUG = registerBlock("empty_mug", new MugBlock(AbstractBlock.Settings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(0.5f).nonOpaque()));
        MUG_OF_MILK = registerBlock("mug_of_milk", new MugBlock(AbstractBlock.Settings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(0.5f).nonOpaque()));
        MUG_OF_CHOCOLATE_MILK = registerBlock("mug_of_chocolate_milk", new MugBlock(AbstractBlock.Settings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(0.5f).nonOpaque()));
        MUG_OF_TRUFFLE_HOT_CHOCOLATE = registerBlock("mug_of_truffle_hot_chocolate", new MugBlock(AbstractBlock.Settings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(0.5f).nonOpaque()));
    }

    private static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
        return (state) -> state.get(Properties.LIT) ? lightValue : 0;
    }

    public static <T extends Block> T registerBlock(String path, T block) {
        return FrozenUp.GENERATOR.block.registerOnlyBlankBlock(block, path);
    }
}
