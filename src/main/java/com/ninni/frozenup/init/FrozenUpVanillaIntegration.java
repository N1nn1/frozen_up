package com.ninni.frozenup.init;

import com.ninni.frozenup.init.FrozenUpItems;
import net.minecraft.world.level.block.ComposterBlock;

public class FrozenUpVanillaIntegration {

    public static void init() {
        ComposterBlock.COMPOSTABLES.put(FrozenUpItems.TRUFFLE.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(FrozenUpItems.TRUFFLE_MUFFIN.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(FrozenUpItems.TRUFFLE_CAKE.get(), 0.5F);
    }

}
