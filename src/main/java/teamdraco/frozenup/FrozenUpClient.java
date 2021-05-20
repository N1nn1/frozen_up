package teamdraco.frozenup;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import teamdraco.frozenup.client.renderer.ChillooRenderer;
import teamdraco.frozenup.init.FrozenUpEntities;

public class FrozenUpClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(FrozenUpEntities.CHILLOO, (dispatcher, context) -> new ChillooRenderer(dispatcher));
    }
}
