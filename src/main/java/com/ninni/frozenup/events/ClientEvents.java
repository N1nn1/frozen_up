package com.ninni.frozenup.events;

import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.client.client.init.FrozenUpEntityModelLayers;
import com.ninni.frozenup.client.model.ChillooEntityModel;
import com.ninni.frozenup.client.model.PenguinEntityModel;
import com.ninni.frozenup.client.model.ReindeerEntityModel;
import com.ninni.frozenup.client.renderer.ChillooEntityRenderer;
import com.ninni.frozenup.client.renderer.PenguinEntityRenderer;
import com.ninni.frozenup.client.renderer.ReindeerEntityRenderer;
import com.ninni.frozenup.init.FrozenUpBlocks;
import com.ninni.frozenup.init.FrozenUpEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = FrozenUp.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(FrozenUpBlocks.EMPTY_MUG.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FrozenUpBlocks.MUG_OF_MILK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FrozenUpBlocks.MUG_OF_CHOCOLATE_MILK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FrozenUpBlocks.MUG_OF_TRUFFLE_HOT_CHOCOLATE.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(FrozenUpEntities.CHILLOO.get(), ChillooEntityRenderer::new);
        event.registerEntityRenderer(FrozenUpEntities.REINDEER.get(), ReindeerEntityRenderer::new);
        event.registerEntityRenderer(FrozenUpEntities.PENGUIN.get(), PenguinEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FrozenUpEntityModelLayers.CHILLOO, ChillooEntityModel::getLayerDefinition);
        event.registerLayerDefinition(FrozenUpEntityModelLayers.REINDEER, ReindeerEntityModel::getLayerDefinition);
        event.registerLayerDefinition(FrozenUpEntityModelLayers.REINDEER_ARMOR, ReindeerEntityModel::getLayerDefinition);
        event.registerLayerDefinition(FrozenUpEntityModelLayers.PENGUIN, PenguinEntityModel::getLayerDefinition);
    }

}
