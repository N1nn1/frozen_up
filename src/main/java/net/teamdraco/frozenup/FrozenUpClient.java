package net.teamdraco.frozenup;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.teamdraco.frozenup.client.renderer.ChillooEntityRenderer;
import net.teamdraco.frozenup.init.FrozenUpBlocks;
import net.teamdraco.frozenup.init.FrozenUpEntities;

@Environment(EnvType.CLIENT)
public class FrozenUpClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(FrozenUpEntities.CHILLOO, ChillooEntityRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlocks(
            RenderLayer.getCutout(),

            FrozenUpBlocks.WINTER_BERRY_BUSH,
            FrozenUpBlocks.EMPTY_MUG,
            FrozenUpBlocks.MUG_OF_MILK,
            FrozenUpBlocks.MUG_OF_CHOCOLATE_MILK,
            FrozenUpBlocks.MUG_OF_TRUFFLE_HOT_CHOCOLATE
        );
    }

    public static Identifier texture(String path) {
        return new Identifier(FrozenUp.MOD_ID, "textures/" + path + ".png");
    }
}
