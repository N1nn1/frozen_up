package com.ninni.frozenup.client.renderer.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ninni.frozenup.entity.ReindeerEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ReindeerArmorFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>>
    extends RenderLayer<T, M> {
    private final ResourceLocation texture;
    private final M model;

    public ReindeerArmorFeatureRenderer(RenderLayerParent<T, M> context, M model, ResourceLocation texture) {
        super(context);
        this.model = model;
        this.texture = texture;
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.CHEST);

        if (!((ReindeerEntity)entity).isWearingArmor()) { return; }

        (this.getParentModel()).copyPropertiesTo(this.model);
        (this.model).prepareMobModel(entity, limbAngle, limbDistance, tickDelta);
        (this.model).setupAnim(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

        (this.model).renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityCutoutNoCull(this.texture)), light, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        this.renderArmor(matrices, vertexConsumers, light, itemStack.hasFoil(), model, 1.0f, 1.0f, 1.0f);
    }

    private void renderArmor(PoseStack matrices, MultiBufferSource vertexConsumers, int light, boolean usesSecondLayer, M model, float red, float green, float blue) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.armorCutoutNoCull(this.texture), false, usesSecondLayer);
        model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0f);
    }
}

