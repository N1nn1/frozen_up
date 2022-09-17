package com.ninni.frozenup.init;

import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.enchantments.HastyHoovesEnchantment;
import com.ninni.frozenup.enchantments.SWEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = FrozenUp.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrozenUpEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, FrozenUp.MOD_ID);

    public static final RegistryObject<Enchantment> HASTY_HOOVES = ENCHANTMENTS.register("hasty_hooves", HastyHoovesEnchantment::new);
    public static final RegistryObject<Enchantment> CLOUD_JUMPER = ENCHANTMENTS.register("cloud_jumper", SWEnchantment::new);

}
