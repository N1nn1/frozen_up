package com.ninni.frozenup.sound;

import com.ninni.frozenup.init.FrozenUpSoundEvents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;

public class FrozenUpBlockSoundGroups {
    public static final SoundType CHILLOO_FEATHER_BLOCK = new ForgeSoundType(
        1, 1,
        FrozenUpSoundEvents.BLOCK_CHILLOO_FEATHER_BLOCK_BREAK,
        () -> SoundEvents.WOOL_STEP,
        FrozenUpSoundEvents.BLOCK_CHILLOO_FEATHER_BLOCK_PLACE,
        () -> SoundEvents.WOOL_HIT,
        () -> SoundEvents.WOOL_FALL
    );
}
