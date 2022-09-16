package com.ninni.frozenup.client.renderer.entity.feature;

import com.ninni.frozenup.entity.ReindeerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ReindeerArmorFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>>
    extends FeatureRenderer<T, M> {
    private final Identifier texture;
    private final M model;

    public ReindeerArmorFeatureRenderer(FeatureRendererContext<T, M> context, M model, Identifier texture) {
        super(context);
        this.model = model;
        this.texture = texture;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack itemStack = entity.getEquippedStack(EquipmentSlot.CHEST);

        if (!((ReindeerEntity)entity).hasArmorInSlot()) { return; }

        (this.getContextModel()).copyStateTo(this.model);
        (this.model).animateModel(entity, limbAngle, limbDistance, tickDelta);
        (this.model).setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

        (this.model).render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(this.texture)), light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        this.renderArmor(matrices, vertexConsumers, light, itemStack.hasGlint(), model, 1.0f, 1.0f, 1.0f);
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, boolean usesSecondLayer, M model, float red, float green, float blue) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(this.texture), false, usesSecondLayer);
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0f);
    }
}

