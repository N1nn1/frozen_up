package com.ninni.frozenup.sound;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class FrozenUpBlockSoundGroups {
    public static final BlockSoundGroup CHILLOO_FEATHER_BLOCK = new BlockSoundGroup(
        1, 1,
        FrozenUpSoundEvents.BLOCK_CHILLOO_FEATHER_BLOCK_BREAK,
        SoundEvents.BLOCK_WOOL_STEP,
        FrozenUpSoundEvents.BLOCK_CHILLOO_FEATHER_BLOCK_PLACE,
        SoundEvents.BLOCK_WOOL_HIT,
        SoundEvents.BLOCK_WOOL_FALL
    );

    public static final BlockSoundGroup COMPACTED_SNOW = new BlockSoundGroup(
        0.3F, 1.0F,

        FrozenUpSoundEvents.BLOCK_COMPACTED_SNOW_BREAK,
        FrozenUpSoundEvents.BLOCK_COMPACTED_SNOW_STEP,
        FrozenUpSoundEvents.BLOCK_COMPACTED_SNOW_PLACE,
        FrozenUpSoundEvents.BLOCK_COMPACTED_SNOW_HIT,
        FrozenUpSoundEvents.BLOCK_COMPACTED_SNOW_FALL
    );
}
