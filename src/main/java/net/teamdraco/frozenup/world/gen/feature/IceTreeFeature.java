package net.teamdraco.frozenup.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class IceTreeFeature extends Feature<DefaultFeatureConfig> {
    public IceTreeFeature(Codec<DefaultFeatureConfig> config) {
        super(config);
    }

    //NOTE all random values below have 1 added to them when randomizing, the values determine the maximum possible output, not number of outputs

    public static int minimumTrunkHeight = 5; //minimum height of the tree trunk
    public static int extraTrunkHeight = 1; //maximum possible randomized increase in height

    public static BlockState logBlock() {
        return Blocks.OAK_LOG.getDefaultState();
    }

    public static BlockState leavesBlock() {
        return Blocks.OAK_LEAVES.getDefaultState();
    }

    @Override
    public boolean generate(StructureWorldAccess reader, ChunkGenerator generator, Random rand, BlockPos pos, DefaultFeatureConfig config) {
        if (reader.isAir(pos.down())) {
            return false;
        }

        WorldGenFiller trunkFiller = new WorldGenFiller();
        WorldGenFiller leavesFiller = new WorldGenFiller();

        boolean higherStart = rand.nextBoolean();
        int extraHeight = rand.nextInt(extraTrunkHeight + 1);
        int height = minimumTrunkHeight + extraHeight + (higherStart ? 1 : 0);

        Direction[] directions = new Direction[]{ Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST };

        for (int i = 0; i <= height; i++) //trunk placement
        {
            BlockPos trunkPos = pos.up(i);
            if (canPlace(reader, trunkPos)) {
                trunkFiller.entries.add(new WorldGenFiller.BlockStateEntry(logBlock(), trunkPos));
            } else {
                return false;
            }
        }
        int leavesEnd = (higherStart ? 2 : 1) + extraHeight;
        for (int i = height + 4; i > leavesEnd; i--) //leaves placement
        {
            int size = height + 4 - i;
            int finalSize = (size + 2) / 4;
            boolean removeCorners = size % 4 >= 2;
            BlockPos leavesCenterPos = pos.up(i);
            makeLeafSlice(leavesFiller, leavesCenterPos, finalSize, removeCorners);
            if (removeCorners && finalSize > 1 && size % 4 == 2) {
                for (Direction direction : directions) {
                    BlockPos leavesTipPos = leavesCenterPos.up(1).offset(direction, 2);
                    leavesFiller.entries.add(new WorldGenFiller.BlockStateEntry(leavesBlock(), leavesTipPos));
                }
            }
        }
        makeLeafSlice(leavesFiller, pos.up(leavesEnd), 1, true);
        trunkFiller.fill(reader, true);
        leavesFiller.fill(reader, true);
        return true;
    }

    public static void makeLeafSlice(WorldGenFiller filler, BlockPos pos, int leavesSize, boolean removeCorners) {
        for (int x = -leavesSize; x <= leavesSize; x++) {
            for (int z = -leavesSize; z <= leavesSize; z++) {
                if (removeCorners) {
                    if (Math.abs(x) == leavesSize && Math.abs(z) == leavesSize) {
                        continue;
                    }
                }
                BlockPos leavesPos = new BlockPos(pos).add(x, 0, z);
                filler.entries.add(new WorldGenFiller.BlockStateEntry(leavesBlock().with(LeavesBlock.DISTANCE, 1), leavesPos));
            }
        }
    }

    public static boolean canPlace(StructureWorldAccess reader, BlockPos pos) {
        if (World.isOutOfBuildLimitVertically(pos)) {
            return false;
        }
        BlockState state = reader.getBlockState(pos);
        return reader.isAir(pos) || state.getMaterial().isReplaceable();
    }
}
