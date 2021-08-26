package net.teamdraco.frozenup.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class IcicleFeature extends Feature<DefaultFeatureConfig> {
    public IcicleFeature(Codec<DefaultFeatureConfig> config) {
        super(config);
    }

    //NOTE all random values below have 1 added to them when randomizing, the values determine the maximum possible output, not number of outputs

    public static int minimumIcicleHeight = 3; //minimum height of the center icicle
    public static int extraIcicleHeight = 1; //maximum possible randomized increase in height

    public static int minimumSideIcicleHeight = 1; //minimum height of side icicles
    public static int extraSideIcicleHeight = 1; //maximum possible randomized increase in height

    public static BlockState iceBlock() {
        return Blocks.PACKED_ICE.getDefaultState();
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> ctx) {
        StructureWorldAccess reader = ctx.getWorld();
        BlockPos pos = ctx.getOrigin();
        Random rand = ctx.getRandom();

        if (reader.isAir(pos.down())) {
            return false;
        }

        WorldGenFiller filler = new WorldGenFiller();

        int height = minimumIcicleHeight + rand.nextInt(extraIcicleHeight + 1);
        Direction[] directions = new Direction[]{ Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST };

        for (int i = 0; i <= height; i++) //trunk placement
        {
            BlockPos icePos = pos.up(i);
            if (canPlace(reader, icePos)) {
                filler.entries.add(new WorldGenFiller.BlockStateEntry(iceBlock(), icePos));
            } else {
                return false;
            }
        }
        for (Direction direction : directions) //side trunk placement
        {
            int sideHeight = minimumSideIcicleHeight + rand.nextInt(extraSideIcicleHeight + 1);
            for (int i = 0; i < sideHeight; i++) {
                BlockPos sideIcePos = pos.offset(direction).up(i);
                if (canPlace(reader, sideIcePos)) {
                    filler.entries.add(new WorldGenFiller.BlockStateEntry(iceBlock(), sideIcePos));
                } else {
                    return false;
                }
            }
            downwardsIce(reader, filler, pos.offset(direction));
        }
        filler.fill(reader, true);
        return true;
    }

    public static void downwardsIce(StructureWorldAccess reader, WorldGenFiller filler, BlockPos pos) {
        int i = 0;
        do {
            i++;
            BlockPos icePos = pos.down(i);
            if (canPlace(reader, icePos)) {
                filler.entries.add(new WorldGenFiller.BlockStateEntry(iceBlock(), icePos));
            } else {
                break;
            }
            if (i > reader.getHeight()) {
                break;
            }
        } while (true);
    }

    public static boolean canPlace(StructureWorldAccess reader, BlockPos pos) {
        if (reader.isOutOfHeightLimit(pos)) {
            return false;
        }
        BlockState state = reader.getBlockState(pos);
        return reader.isAir(pos) || state.getMaterial().isReplaceable();
    }
}
