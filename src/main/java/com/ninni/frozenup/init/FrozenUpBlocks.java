package com.ninni.frozenup.init;

import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.block.FeatherLampBlock;
import com.ninni.frozenup.block.FiberCoveringBlock;
import com.ninni.frozenup.block.MugBlock;
import com.ninni.frozenup.block.VerticalSlabBlock;
import com.ninni.frozenup.block.vanilla.TruffleCakeBlock;
import com.ninni.frozenup.sound.FrozenUpBlockSoundGroups;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

@Mod.EventBusSubscriber(modid = FrozenUp.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrozenUpBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FrozenUp.MOD_ID);

    public static final RegistryObject<Block> CHILLOO_FEATHER_BLOCK = register("chilloo_feather_block", () -> new Block(BlockBehaviour.Properties.of(Material.GRASS).strength(0.1f).sound(FrozenUpBlockSoundGroups.CHILLOO_FEATHER_BLOCK)));
    public static final RegistryObject<Block> CHILLOO_FEATHER_COVERING = register("chilloo_feather_covering", () -> new FiberCoveringBlock(BlockBehaviour.Properties.copy(CHILLOO_FEATHER_BLOCK.get())));
    public static final RegistryObject<Block> CHILLOO_FEATHER_LAMP = register("chilloo_feather_lamp", () -> new FeatherLampBlock(BlockBehaviour.Properties.of(Material.GRASS).strength(0.3F).sound(FrozenUpBlockSoundGroups.CHILLOO_FEATHER_BLOCK).lightLevel(createLightLevelFromLitBlockState(10))));

    public static final RegistryObject<Block> TRUFFLE_CAKE = register("truffle_cake", () -> new TruffleCakeBlock(BlockBehaviour.Properties.of(Material.CAKE).strength(0.5F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> CUT_ICE = register("cut_ice", () -> new Block(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE)));
    public static final RegistryObject<Block> CUT_ICE_STAIRS = register("cut_ice_stairs", () -> new StairBlock(CUT_ICE.get().defaultBlockState(), BlockBehaviour.Properties.copy(CUT_ICE.get())));
    public static final RegistryObject<Block> CUT_ICE_SLAB = register("cut_ice_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CUT_ICE.get())));
    public static final RegistryObject<Block> CUT_ICE_CUBES = register("cut_ice_cubes", () -> new Block(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE)));
    public static final RegistryObject<Block> CUT_ICE_CUBE_STAIRS = register("cut_ice_cube_stairs", () -> new StairBlock(CUT_ICE_CUBES.get().defaultBlockState(), BlockBehaviour.Properties.copy(CUT_ICE_CUBES.get())));
    public static final RegistryObject<Block> CUT_ICE_CUBE_SLAB = register("cut_ice_cube_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CUT_ICE_CUBES.get())));
    public static final RegistryObject<Block> CUT_ICE_CUBE_WALL = register("cut_ice_cube_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(CUT_ICE_CUBES.get())));

    public static final RegistryObject<Block> EMPTY_MUG = register("empty_mug", () -> new MugBlock(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).noOcclusion()));
    public static final RegistryObject<Block> MUG_OF_MILK = register("mug_of_milk", () -> new MugBlock(FrozenUpItems.MUG_OF_MILK, BlockBehaviour.Properties.copy(EMPTY_MUG.get())));
    public static final RegistryObject<Block> MUG_OF_CHOCOLATE_MILK = register("mug_of_chocolate_milk", () -> new MugBlock(FrozenUpItems.MUG_OF_CHOCOLATE_MILK, BlockBehaviour.Properties.copy(EMPTY_MUG.get())));
    public static final RegistryObject<Block> MUG_OF_TRUFFLE_HOT_CHOCOLATE = register("mug_of_truffle_hot_chocolate", () -> new MugBlock(FrozenUpItems.MUG_OF_TRUFFLE_HOT_CHOCOLATE, BlockBehaviour.Properties.copy(EMPTY_MUG.get())));

    public static final RegistryObject<Block> CUT_ICE_VERTICAL_SLAB = compatRegister("quark", "cut_ice_vertical_slab", () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(CUT_ICE.get())));
    public static final RegistryObject<Block> CUT_ICE_CUBE_VERTICAL_SLAB = compatRegister("quark", "cut_ice_cube_vertical_slab", () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(CUT_ICE_CUBES.get())));

    public static final RegistryObject<Block> COMPACTED_SNOW_BRICKS = BLOCKS.register("compacted_snow_bricks", () -> new Block(BlockBehaviour.Properties.of(Material.SNOW).requiresCorrectToolForDrops().strength(0.4F).sound(FrozenUpBlockSoundGroups.COMPACTED_SNOW)));
    public static final RegistryObject<Block> COMPACTED_SNOW_BRICK_STAIRS = BLOCKS.register("compacted_snow_brick_stairs", () -> new StairBlock(COMPACTED_SNOW_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(COMPACTED_SNOW_BRICKS.get())));
    public static final RegistryObject<Block> COMPACTED_SNOW_BRICK_SLAB = BLOCKS.register("compacted_snow_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(COMPACTED_SNOW_BRICKS.get())));
    public static final RegistryObject<Block> COMPACTED_SNOW_BRICK_VERTICAL_SLAB = BLOCKS.register("compacted_snow_brick_vertical_slab", () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(COMPACTED_SNOW_BRICKS.get())));
    public static final RegistryObject<Block> COMPACTED_SNOW_FOUNDATION = BLOCKS.register("compacted_snow_foundation", () -> new Block(BlockBehaviour.Properties.copy(COMPACTED_SNOW_BRICKS.get())));

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return (state) -> (Boolean)state.getValue(BlockStateProperties.LIT) ? litLevel : 0;
    }

    private static <B extends Block> RegistryObject<B> compatRegister(String modid, String id, Supplier<B> supplier) {
        return compatRegister(modid, id, supplier, true);
    }

    private static <B extends Block> RegistryObject<B> compatRegister(String modid, String id, Supplier<B> supplier, boolean registerItem) {
        RegistryObject<B> block = BLOCKS.register(id, supplier);
        if (registerItem) {
            FrozenUpItems.ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties().tab(ModList.get().isLoaded(modid) ? FrozenUp.ITEM_GROUP : null)));
        }
        return block;
    }

    private static <B extends Block> RegistryObject<B> register(String id, Supplier<B> supplier, boolean registerItem) {
        RegistryObject<B> block = BLOCKS.register(id, supplier);
        if (registerItem) {
            FrozenUpItems.ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties().tab(FrozenUp.ITEM_GROUP)));
        }
        return block;
    }

    private static <B extends Block> RegistryObject<B> register(String id, Supplier<B> block) {
        return register(id, block, false);
    }
}
