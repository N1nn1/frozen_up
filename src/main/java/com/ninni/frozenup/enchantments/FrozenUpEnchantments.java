package com.ninni.frozenup.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.ninni.frozenup.FrozenUp.*;

public class FrozenUpEnchantments {

    public static final Enchantment HASTY_HOOVES = register("hasty_hooves", new HastyHoovesEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.WEARABLE, new EquipmentSlot[]{EquipmentSlot.CHEST}));
    public static final Enchantment CLOUD_JUMPER = register("cloud_jumper", new FrozenUpEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.WEARABLE, new EquipmentSlot[]{EquipmentSlot.CHEST}));

    private static Enchantment register(String id, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, id), enchantment);
    }

}
