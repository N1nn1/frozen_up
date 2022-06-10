package com.ninni.frozenup.client.renderer.entity.feature;

import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.client.model.ChillooEntityModel;
import com.ninni.frozenup.client.renderer.ChillooEntityRenderer;
import com.ninni.frozenup.entity.ChillooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ChillooBandsFeatureRenderer extends FeatureRenderer<ChillooEntity, ChillooEntityModel> {
    public static final Identifier BANDS = new Identifier(FrozenUp.MOD_ID, "textures/entity/chilloo/chilloo_bands.png");

    public <T extends LivingEntity> ChillooBandsFeatureRenderer(ChillooEntityRenderer featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, ChillooEntity chilloo, float f, float g, float h, float j, float k, float l) {
        if (chilloo.isTamed() && !chilloo.isInvisible()) {
            float[] fs = chilloo.getBandsColor().getColorComponents();
            renderModel(this.getContextModel(), BANDS, matrixStack, vertexConsumerProvider, i, chilloo, fs[0], fs[1], fs[2]);
        }
    }
}
