package com.ninni.frozenup.client.renderer.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.client.model.ChillooEntityModel;
import com.ninni.frozenup.client.renderer.ChillooEntityRenderer;
import com.ninni.frozenup.entity.ChillooEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChillooBandsFeatureRenderer extends RenderLayer<ChillooEntity, ChillooEntityModel> {
    public static final ResourceLocation BANDS = new ResourceLocation(FrozenUp.MOD_ID, "textures/entity/chilloo/chilloo_bands.png");

    public ChillooBandsFeatureRenderer(ChillooEntityRenderer featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, ChillooEntity chilloo, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        if (chilloo.isTame() && !chilloo.isInvisible()) {
            float[] fs = chilloo.getBandsColor().getTextureDiffuseColors();
            renderColoredCutoutModel(this.getParentModel(), BANDS, matrixStack, vertexConsumerProvider, i, chilloo, fs[0], fs[1], fs[2]);
        }
    }

}
