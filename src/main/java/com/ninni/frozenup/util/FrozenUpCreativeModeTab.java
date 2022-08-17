package com.ninni.frozenup.util;

import com.ninni.frozenup.init.FrozenUpItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class FrozenUpCreativeModeTab extends CreativeModeTab {

    public FrozenUpCreativeModeTab(String label) {
        super(label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(FrozenUpItems.FROZENUP.get());
    }
}
