package com.ninni.frozenup.init;

import com.ninni.frozenup.FrozenUp;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = FrozenUp.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrozenUpCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FrozenUp.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FROZEN_UP = CREATIVE_MODE_TABS.register("frozen_up", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.frozenup.item_group")).icon(FrozenUpItems.FROZENUP.get()::getDefaultInstance).displayItems((parameters, output) -> {
        //spawn eggs
        output.accept(FrozenUpItems.CHILLOO_SPAWN_EGG.get());
        output.accept(FrozenUpItems.REINDEER_SPAWN_EGG.get());
        output.accept(FrozenUpItems.PENGUIN_SPAWN_EGG.get());
        //truffle related items
        output.accept(FrozenUpItems.FROZEN_TRUFFLE.get());
        output.accept(FrozenUpItems.TRUFFLE.get());
        output.accept(FrozenUpItems.TRUFFLE_MUFFIN.get());
        output.accept(FrozenUpItems.TRUFFLE_CAKE.get());
        //misc items
        output.accept(FrozenUpItems.PINECONE.get());
        output.accept(FrozenUpItems.HOOF_ARMOR.get());
        //mugs
        output.accept(FrozenUpItems.EMPTY_MUG.get());
        output.accept(FrozenUpItems.MUG_OF_MILK.get());
        output.accept(FrozenUpItems.MUG_OF_CHOCOLATE_MILK.get());
        output.accept(FrozenUpItems.MUG_OF_TRUFFLE_HOT_CHOCOLATE.get());
        //snow bricks
        output.accept(FrozenUpItems.COMPACTED_SNOW_BRICK.get());
        output.accept(FrozenUpItems.COMPACTED_SNOW_BRICKS.get());
        output.accept(FrozenUpItems.COMPACTED_SNOW_BRICK_STAIRS.get());
        output.accept(FrozenUpItems.COMPACTED_SNOW_BRICK_SLAB.get());
        if (ModList.get().isLoaded("quark")) {
            output.accept(FrozenUpItems.COMPACTED_SNOW_BRICK_VERTICAL_SLAB.get());
        }
        output.accept(FrozenUpItems.COMPACTED_SNOW_FOUNDATION.get());
        //chilloo related items
        output.accept(FrozenUpItems.CHILLOO_FEATHER.get());
        output.accept(FrozenUpItems.CHILLOO_FEATHER_BLOCK.get());
        output.accept(FrozenUpItems.CHILLOO_FEATHER_COVERING.get());
        output.accept(FrozenUpItems.CHILLOO_FEATHER_LAMP.get());
        //ice blocks
        output.accept(FrozenUpItems.CUT_ICE.get());
        output.accept(FrozenUpItems.CUT_ICE_STAIRS.get());
        output.accept(FrozenUpItems.CUT_ICE_SLAB.get());
        if (ModList.get().isLoaded("quark")) {
            output.accept(FrozenUpItems.CUT_ICE_VERTICAL_SLAB.get());
        }
        output.accept(FrozenUpItems.CUT_ICE_CUBES.get());
        output.accept(FrozenUpItems.CUT_ICE_CUBE_STAIRS.get());
        output.accept(FrozenUpItems.CUT_ICE_CUBE_SLAB.get());
        if (ModList.get().isLoaded("quark")) {
            output.accept(FrozenUpItems.CUT_ICE_CUBE_VERTICAL_SLAB.get());
        }
        output.accept(FrozenUpItems.CUT_ICE_CUBE_WALL.get());
    }).build());

}
