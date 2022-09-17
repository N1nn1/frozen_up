package com.ninni.frozenup.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SWEnchantment extends Enchantment {

    public SWEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEARABLE, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

}
