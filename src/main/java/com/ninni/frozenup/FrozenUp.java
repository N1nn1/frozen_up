package com.ninni.frozenup;

import com.ninni.frozenup.crafting.conditions.QuarkCondition;
import com.ninni.frozenup.events.MiscEvents;
import com.ninni.frozenup.events.MobEvents;
import com.ninni.frozenup.init.FrozenUpBlocks;
import com.ninni.frozenup.init.FrozenUpCreativeModeTabs;
import com.ninni.frozenup.init.FrozenUpEnchantments;
import com.ninni.frozenup.init.FrozenUpEntities;
import com.ninni.frozenup.init.FrozenUpItems;
import com.ninni.frozenup.init.FrozenUpLootModifiers;
import com.ninni.frozenup.init.FrozenUpSoundEvents;
import com.ninni.frozenup.init.FrozenUpVanillaIntegration;
import com.ninni.frozenup.network.FrozenUpNetworkHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FrozenUp.MOD_ID)
public class FrozenUp {
    public static final String MOD_ID = "frozenup";

    public FrozenUp() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        modEventBus.addListener(this::commonSetup);
        FrozenUpBlocks.BLOCKS.register(modEventBus);
        FrozenUpCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        FrozenUpEntities.ENTITY_TYPE.register(modEventBus);
        FrozenUpEnchantments.ENCHANTMENTS.register(modEventBus);
        FrozenUpItems.ITEMS.register(modEventBus);
        FrozenUpLootModifiers.LOOT_MODIFIERS.register(modEventBus);
        FrozenUpSoundEvents.SOUND_EVENTS.register(modEventBus);

        CraftingHelper.register(new QuarkCondition.Serializer());

        eventBus.register(this);

        eventBus.register(new MiscEvents());
        eventBus.register(new MobEvents());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FrozenUpVanillaIntegration.init();
            FrozenUpNetworkHandler.init();
        });
    }

}
