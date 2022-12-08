package com.ninni.frozenup.enchantments;

import com.ninni.frozenup.item.HoofArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FrozenUpEnchantment extends Enchantment {

    public FrozenUpEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEARABLE, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof HoofArmorItem;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }
}
