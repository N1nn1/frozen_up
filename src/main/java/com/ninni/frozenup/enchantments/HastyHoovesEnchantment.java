package com.ninni.frozenup.enchantments;

public class HastyHoovesEnchantment extends FrozenUpEnchantment {

    public HastyHoovesEnchantment() {
        super();
    }

    @Override
    public int getMinCost(int level) {
        return 5 + (level - 1) * 8;
    }

    @Override
    public int getMaxCost(int level) {
        return super.getMaxCost(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

}
