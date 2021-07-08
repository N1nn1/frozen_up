package teamdraco.frozenup.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import teamdraco.frozenup.FrozenUp;
import teamdraco.frozenup.block.CarpetBlock;
import teamdraco.frozenup.block.FeatherLampBlock;
import teamdraco.frozenup.block.MugBlock;

import java.util.function.ToIntFunction;

@SuppressWarnings("unused")
public class FrozenUpBlocks {

        public static final Block CHILLOO_FEATHER_BLOCK = register("chilloo_feather_block", new Block(FabricBlockSettings.of(Material.SOLID_ORGANIC).strength(0.1f).sounds(BlockSoundGroup.SNOW)), false);
        public static final Block CHILLOO_FEATHER_BLOCK_CARPET = register("chilloo_feather_block_carpet", new CarpetBlock(FabricBlockSettings.copyOf(CHILLOO_FEATHER_BLOCK)), false);
        public static final Block TRUFFLE_CAKE = register("truffle_cake", new CakeBlock(AbstractBlock.Settings.of(Material.CAKE).strength(0.5F).sounds(BlockSoundGroup.WOOL)), false);
        public static final Block CHILLOO_FEATHER_LAMP = register("chilloo_feather_lamp", new FeatherLampBlock(AbstractBlock.Settings.of(Material.SOLID_ORGANIC).strength(0.3F).sounds(BlockSoundGroup.SNOW).luminance(createLightLevelFromLitBlockState(10))), false);
        public static final Block EMPTY_MUG = register("empty_mug", new MugBlock(AbstractBlock.Settings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(0.5f).nonOpaque()), false);
        public static final Block MUG_OF_MILK = register("mug_of_milk", new MugBlock(AbstractBlock.Settings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(0.5f).nonOpaque()), false);
        public static final Block MUG_OF_CHOCOLATE_MILK = register("mug_of_chocolate_milk", new MugBlock(AbstractBlock.Settings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(0.5f).nonOpaque()), false);
        public static final Block MUG_OF_TRUFFLE_HOT_CHOCOLATE = register("mug_of_truffle_hot_chocolate", new MugBlock(AbstractBlock.Settings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(0.5f).nonOpaque()), false);

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return (state) -> (Boolean)state.get(Properties.LIT) ? litLevel : 0;
    }

    private static Block register(String id, Block block, boolean registerItem) {
        Block registered = Registry.register(Registry.BLOCK, new Identifier(FrozenUp.MOD_ID, id), block);
        if (registerItem) {
            Registry.register(Registry.ITEM, new Identifier(FrozenUp.MOD_ID, id), new BlockItem(registered, new FabricItemSettings().group(FrozenUp.ITEM_GROUP)));
        }
        return registered;
    }
    private static Block register(String id, Block block) {
        return register(id, block, true);
    }
}