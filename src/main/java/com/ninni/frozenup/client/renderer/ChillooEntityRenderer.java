package com.ninni.frozenup.client.renderer;

import com.ninni.frozenup.client.client.init.FrozenUpEntityModelLayers;
import com.ninni.frozenup.client.model.ChillooEntityModel;
import com.ninni.frozenup.client.renderer.entity.feature.ChillooHeldItemFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import com.ninni.frozenup.client.renderer.entity.feature.ChillooBandsFeatureRenderer;
import com.ninni.frozenup.entity.ChillooEntity;

import static com.ninni.frozenup.FrozenUp.*;

@Environment(EnvType.CLIENT)
public class ChillooEntityRenderer extends MobEntityRenderer<ChillooEntity, ChillooEntityModel> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/entity/chilloo/chilloo.png");
    private static final Identifier TAMED_TEXTURE = new Identifier(MOD_ID, "textures/entity/chilloo/chilloo_tamed.png");
    private static final Identifier SHEARED_TEXTURE = new Identifier(MOD_ID, "textures/entity/chilloo/chilloo_sheared.png");
    private static final Identifier TAMED_SHEARED_TEXTURE = new Identifier(MOD_ID, "textures/entity/chilloo/chilloo_tamed_sheared.png");

    public ChillooEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ChillooEntityModel(ctx.getPart(FrozenUpEntityModelLayers.CHILLOO)), 0.6F);
        this.addFeature(new ChillooBandsFeatureRenderer(this));
        this.addFeature(new ChillooHeldItemFeatureRenderer(this, ctx.getHeldItemRenderer()));
    }

    @Override
    protected boolean isShaking(ChillooEntity entity) {
        if (entity.isSheared() && entity.getWorld().getBiome(entity.getBlockPos()).isIn(ConventionalBiomeTags.CLIMATE_COLD)) return true;
        return super.isShaking(entity);
    }

    @Override public Identifier getTexture(ChillooEntity entity) {
        if (entity.isSheared()) return entity.isTamed() ? TAMED_SHEARED_TEXTURE : SHEARED_TEXTURE;
        else return entity.isTamed() ? TAMED_TEXTURE : TEXTURE;
    }
}
