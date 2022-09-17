package com.ninni.frozenup.enchantments;

public class HastyHoovesEnchantment extends SWEnchantment {

    public HastyHoovesEnchantment() {
        super();
    }

    @Override
    public int getMinCost(int pLevel) {
        return 5 + (pLevel - 1) * 8;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return super.getMaxCost(pLevel) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

}
