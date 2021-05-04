package teamdraco.frozenup.init;

import com.github.evoslab.cookiecore.datagen.MainGenerator;
import teamdraco.frozenup.FrozenUp;
import teamdraco.frozenup.block.CarpetBlock;
import teamdraco.frozenup.block.FeatherLampBlock;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;

import java.util.function.ToIntFunction;

public class FrozenUpBlocks {
	private static final MainGenerator generator = new MainGenerator(FrozenUp.MOD_ID);

	public static Block CHILLOO_FEATHER_BLOCK;
	public static Block CHILLOO_FEATHER_BLOCK_CARPET;
	public static Block TRUFFLE_CAKE;
	public static Block CHILLOO_FEATHER_LAMP;

	private static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
		return (state) -> state.get(Properties.LIT) ? lightValue : 0;
	}

	public static Block registerBlocks(String path, Block block) {
		return generator.block.registerOnlyBlankBlock(block, path);
	}

	public static void init(){
		CHILLOO_FEATHER_BLOCK = FrozenUpBlocks.registerBlocks("chilloo_feather_block", new Block(AbstractBlock.Settings.of(Material.SOLID_ORGANIC).sounds(BlockSoundGroup.SNOW).strength(0.1f)));
		CHILLOO_FEATHER_BLOCK_CARPET = FrozenUpBlocks.registerBlocks("chilloo_feather_block_carpet", new CarpetBlock(AbstractBlock.Settings.of(Material.SOLID_ORGANIC).sounds(BlockSoundGroup.SNOW).strength(0.1f)));
		TRUFFLE_CAKE = FrozenUpBlocks.registerBlocks("truffle_cake", new CakeBlock(AbstractBlock.Settings.of(Material.CAKE).strength(0.5F).sounds(BlockSoundGroup.WOOL)));
		CHILLOO_FEATHER_LAMP = FrozenUpBlocks.registerBlocks("chilloo_feather_lamp", new FeatherLampBlock(AbstractBlock.Settings.of(Material.SNOW_BLOCK).luminance(getLightValueLit(10)).sounds(BlockSoundGroup.SNOW).strength(0.3F)));
	}

}