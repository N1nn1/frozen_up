package net.teamdraco.frozenup.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;

import java.util.ArrayList;

public class WorldGenFiller {
    public ArrayList<BlockStateEntry> entries = new ArrayList<>();

    public WorldGenFiller() {}

    public void fill(StructureWorldAccess world, boolean safetyCheck) {
        for (BlockStateEntry entry : entries) {
            if (safetyCheck && !entry.canPlace(world)) {
                continue;
            }
            world.setBlockState(entry.pos, entry.state, 3);
            entry.additionalPlacement(world);
            if (world instanceof World) {
                BlockState state = world.getBlockState(entry.pos);
                ((World) world).updateListeners(entry.pos, state, state, 2);
            }
        }
    }

    public static class BlockStateEntry {
        public final BlockState state;
        public final BlockPos pos;

        public BlockStateEntry(BlockState state, BlockPos pos) {
            this.state = state;

            this.pos = pos;
        }

        public boolean canPlace(StructureWorldAccess reader) {
            return canPlace(reader, pos);
        }

        public boolean canPlace(StructureWorldAccess reader, BlockPos pos) {
            if (reader.isOutOfHeightLimit(pos)) {
                return false;
            }
            BlockState state = reader.getBlockState(pos);
            return reader.isAir(pos) || state.getMaterial().isReplaceable();
        }

        public boolean canPlace(StructureWorldAccess reader, BlockPos pos, Block block) {
            if (reader.isOutOfHeightLimit(pos)) {
                return false;
            }
            BlockState state = reader.getBlockState(pos);
            return state.getBlock().equals(block) || reader.isAir(pos) || state.getMaterial().isReplaceable();
        }

        public void additionalPlacement(StructureWorldAccess reader) {}
    }
}
