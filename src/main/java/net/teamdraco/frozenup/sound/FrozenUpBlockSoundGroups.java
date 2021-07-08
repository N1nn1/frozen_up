package net.teamdraco.frozenup.sound;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.teamdraco.frozenup.init.FrozenUpSoundEvents;

public class FrozenUpBlockSoundGroups {
    public static final BlockSoundGroup CHILLOO_FEATHER_BLOCK = new BlockSoundGroup(
        1, 1,
        FrozenUpSoundEvents.BLOCK_CHILLOO_FEATHER_BLOCK_BREAK,
        SoundEvents.BLOCK_WOOL_STEP,
        FrozenUpSoundEvents.BLOCK_CHILLOO_FEATHER_BLOCK_PLACE,
        SoundEvents.BLOCK_WOOL_HIT,
        SoundEvents.BLOCK_WOOL_FALL
    );
}
