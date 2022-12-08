package com.ninni.frozenup.events;

import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.client.client.init.FrozenUpEntityModelLayers;
import com.ninni.frozenup.client.model.ChillooEntityModel;
import com.ninni.frozenup.client.model.PenguinEntityModel;
import com.ninni.frozenup.client.model.ReindeerEntityModel;
import com.ninni.frozenup.client.renderer.ChillooEntityRenderer;
import com.ninni.frozenup.client.renderer.PenguinEntityRenderer;
import com.ninni.frozenup.client.renderer.ReindeerEntityRenderer;
import com.ninni.frozenup.init.FrozenUpEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FrozenUp.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

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
