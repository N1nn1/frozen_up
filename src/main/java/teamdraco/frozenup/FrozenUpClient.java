package teamdraco.frozenup;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import teamdraco.frozenup.client.renderer.ChillooRenderer;
import teamdraco.frozenup.init.FrozenUpBlocks;
import teamdraco.frozenup.init.FrozenUpEntities;

public class FrozenUpClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(FrozenUpEntities.CHILLOO, (dispatcher, context) -> new ChillooRenderer(dispatcher));

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
                FrozenUpBlocks.EMPTY_MUG,
                FrozenUpBlocks.MUG_OF_MILK,
                FrozenUpBlocks.MUG_OF_CHOCOLATE_MILK,
                FrozenUpBlocks.MUG_OF_TRUFFLE_HOT_CHOCOLATE
        );
    }
}
