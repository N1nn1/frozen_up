package com.ninni.frozenup.init;

import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.block.FeatherLampBlock;
import com.ninni.frozenup.block.FiberCoveringBlock;
import com.ninni.frozenup.block.MugBlock;
import com.ninni.frozenup.block.VerticalSlabBlock;
import com.ninni.frozenup.block.vanilla.TruffleCakeBlock;
import com.ninni.frozenup.sound.FrozenUpBlockSoundGroups;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.ToIntFunction;

@Mod.EventBusSubscriber(modid = FrozenUp.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrozenUpBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FrozenUp.MOD_ID);

    public static final RegistryObject<Block> CHILLOO_FEATHER_BLOCK = BLOCKS.register("chilloo_feather_block", () -> new Block(BlockBehaviour.Properties.of().strength(0.1f).sound(FrozenUpBlockSoundGroups.CHILLOO_FEATHER_BLOCK)));
    public static final RegistryObject<Block> CHILLOO_FEATHER_COVERING = BLOCKS.register("chilloo_feather_covering", () -> new FiberCoveringBlock(BlockBehaviour.Properties.copy(CHILLOO_FEATHER_BLOCK.get())));
    public static final RegistryObject<Block> CHILLOO_FEATHER_LAMP = BLOCKS.register("chilloo_feather_lamp", () -> new FeatherLampBlock(BlockBehaviour.Properties.of().strength(0.3F).sound(FrozenUpBlockSoundGroups.CHILLOO_FEATHER_BLOCK).lightLevel(createLightLevelFromLitBlockState(10))));

    public static final RegistryObject<Block> TRUFFLE_CAKE = BLOCKS.register("truffle_cake", () -> new TruffleCakeBlock(BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> CUT_ICE = BLOCKS.register("cut_ice", () -> new Block(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE)));
    public static final RegistryObject<Block> CUT_ICE_STAIRS = BLOCKS.register("cut_ice_stairs", () -> new StairBlock(CUT_ICE.get().defaultBlockState(), BlockBehaviour.Properties.copy(CUT_ICE.get())));
    public static final RegistryObject<Block> CUT_ICE_SLAB = BLOCKS.register("cut_ice_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CUT_ICE.get())));
    public static final RegistryObject<Block> CUT_ICE_VERTICAL_SLAB = BLOCKS.register("cut_ice_vertical_slab", () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(CUT_ICE.get())));
    public static final RegistryObject<Block> CUT_ICE_CUBES = BLOCKS.register("cut_ice_cubes", () -> new Block(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE)));
    public static final RegistryObject<Block> CUT_ICE_CUBE_STAIRS = BLOCKS.register("cut_ice_cube_stairs", () -> new StairBlock(CUT_ICE_CUBES.get().defaultBlockState(), BlockBehaviour.Properties.copy(CUT_ICE_CUBES.get())));
    public static final RegistryObject<Block> CUT_ICE_CUBE_SLAB = BLOCKS.register("cut_ice_cube_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CUT_ICE_CUBES.get())));
    public static final RegistryObject<Block> CUT_ICE_CUBE_WALL = BLOCKS.register("cut_ice_cube_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(CUT_ICE_CUBES.get())));
    public static final RegistryObject<Block> CUT_ICE_CUBE_VERTICAL_SLAB = BLOCKS.register("cut_ice_cube_vertical_slab", () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(CUT_ICE_CUBES.get())));

    public static final RegistryObject<Block> EMPTY_MUG = BLOCKS.register("empty_mug", () -> new MugBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).noOcclusion()));
    public static final RegistryObject<Block> MUG_OF_MILK = BLOCKS.register("mug_of_milk", () -> new MugBlock(FrozenUpItems.MUG_OF_MILK, BlockBehaviour.Properties.copy(EMPTY_MUG.get())));
    public static final RegistryObject<Block> MUG_OF_CHOCOLATE_MILK = BLOCKS.register("mug_of_chocolate_milk", () -> new MugBlock(FrozenUpItems.MUG_OF_CHOCOLATE_MILK, BlockBehaviour.Properties.copy(EMPTY_MUG.get())));
    public static final RegistryObject<Block> MUG_OF_TRUFFLE_HOT_CHOCOLATE = BLOCKS.register("mug_of_truffle_hot_chocolate", () -> new MugBlock(FrozenUpItems.MUG_OF_TRUFFLE_HOT_CHOCOLATE, BlockBehaviour.Properties.copy(EMPTY_MUG.get())));

    public static final RegistryObject<Block> COMPACTED_SNOW_BRICKS = BLOCKS.register("compacted_snow_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE).requiresCorrectToolForDrops().strength(0.4F).sound(FrozenUpBlockSoundGroups.COMPACTED_SNOW)));
    public static final RegistryObject<Block> COMPACTED_SNOW_BRICK_STAIRS = BLOCKS.register("compacted_snow_brick_stairs", () -> new StairBlock(COMPACTED_SNOW_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(COMPACTED_SNOW_BRICKS.get())));
    public static final RegistryObject<Block> COMPACTED_SNOW_BRICK_SLAB = BLOCKS.register("compacted_snow_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(COMPACTED_SNOW_BRICKS.get())));
    public static final RegistryObject<Block> COMPACTED_SNOW_BRICK_VERTICAL_SLAB = BLOCKS.register("compacted_snow_brick_vertical_slab", () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(COMPACTED_SNOW_BRICKS.get())));
    public static final RegistryObject<Block> COMPACTED_SNOW_FOUNDATION = BLOCKS.register("compacted_snow_foundation", () -> new Block(BlockBehaviour.Properties.copy(COMPACTED_SNOW_BRICKS.get())));

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return (state) -> (Boolean)state.getValue(BlockStateProperties.LIT) ? litLevel : 0;
    }

}
