package com.ninni.frozenup.client.renderer.entity.feature;

import com.ninni.frozenup.client.model.ReindeerEntityModel;
import com.ninni.frozenup.client.renderer.ReindeerEntityRenderer;
import com.ninni.frozenup.entity.ReindeerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static com.ninni.frozenup.FrozenUp.*;

@Environment(EnvType.CLIENT)
public class ReindeerFestiveOverlayFeatureRenderer extends EyesFeatureRenderer<ReindeerEntity, ReindeerEntityModel<ReindeerEntity>> {
    private static final RenderLayer OVERLAY = RenderLayer.getEyes(new Identifier(MOD_ID, "textures/entity/reindeer/reindeer_festive_overlay.png"));

    public ReindeerFestiveOverlayFeatureRenderer(FeatureRendererContext<ReindeerEntity, ReindeerEntityModel<ReindeerEntity>> context) { super(context); }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ReindeerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (ReindeerEntityRenderer.isFestive(entity)) super.render(matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return OVERLAY;
    }
}
