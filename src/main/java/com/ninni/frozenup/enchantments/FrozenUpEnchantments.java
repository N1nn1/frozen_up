package com.ninni.frozenup.enchantments;

import com.ninni.frozenup.FrozenUp;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FrozenUpEnchantments {

    public static final Enchantment HASTY_HOOVES = register("hasty_hooves", new HastyHoovesEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.WEARABLE, new EquipmentSlot[]{EquipmentSlot.CHEST}));
    public static final Enchantment CLOUD_JUMPER = register("cloud_jumper", new FrozenUpEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.WEARABLE, new EquipmentSlot[]{EquipmentSlot.CHEST}));


    private static Enchantment register(String id, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(FrozenUp.MOD_ID, id), enchantment);
    }
}
