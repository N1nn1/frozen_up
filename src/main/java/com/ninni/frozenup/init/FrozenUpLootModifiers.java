package com.ninni.frozenup.init;

import com.mojang.serialization.Codec;
import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.util.PineconeLootModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = FrozenUp.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrozenUpLootModifiers {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, FrozenUp.MOD_ID);

    public static final RegistryObject<Codec<PineconeLootModifier>> PINECONE_LOOT_MODIFIER = LOOT_MODIFIERS.register("pinecone_loot_modifier", () -> PineconeLootModifier.CODEC);

}
