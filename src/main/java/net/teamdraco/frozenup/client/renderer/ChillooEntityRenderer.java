package net.teamdraco.frozenup.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.teamdraco.frozenup.FrozenUp;
import net.teamdraco.frozenup.client.client.init.FrozenUpEntityModelLayers;
import net.teamdraco.frozenup.client.model.ChillooEntityModel;
import net.teamdraco.frozenup.client.renderer.entity.feature.ChillooBandsFeatureRenderer;
import net.teamdraco.frozenup.entity.ChillooEntity;

@Environment(EnvType.CLIENT)
public class ChillooEntityRenderer extends MobEntityRenderer<ChillooEntity, ChillooEntityModel> {
    private static final Identifier TEXTURE = new Identifier(FrozenUp.MOD_ID, "textures/entity/chilloo/chilloo.png");
    private static final Identifier TAMED_TEXTURE = new Identifier(FrozenUp.MOD_ID, "textures/entity/chilloo/chilloo_tamed.png");

    public ChillooEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ChillooEntityModel(ctx.getPart(FrozenUpEntityModelLayers.CHILLOO)), 0.6F);
        this.addFeature(new ChillooBandsFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(ChillooEntity entity) {
        return entity.isTamed() ? TAMED_TEXTURE : TEXTURE;
    }
}
