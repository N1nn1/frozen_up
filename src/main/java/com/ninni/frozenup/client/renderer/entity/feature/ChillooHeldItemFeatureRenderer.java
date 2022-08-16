package com.ninni.frozenup.client.renderer.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.ninni.frozenup.client.model.ChillooEntityModel;
import com.ninni.frozenup.client.renderer.ChillooEntityRenderer;
import com.ninni.frozenup.entity.ChillooEntity;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChillooHeldItemFeatureRenderer extends RenderLayer<ChillooEntity, ChillooEntityModel> {
    private final ItemInHandRenderer heldItemRenderer;

    public ChillooHeldItemFeatureRenderer(ChillooEntityRenderer context, ItemInHandRenderer heldItemRenderer) {
        super(context);
        this.heldItemRenderer = heldItemRenderer;
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, ChillooEntity chilloo, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        boolean baby = chilloo.isBaby();

        matrices.pushPose();
        float headRoll;
        if (baby) {
            matrices.scale(0.75F, 0.75F, 0.75F);
            matrices.translate(0.0, 0.5, 0.2);
        }
        matrices.translate(this.getParentModel().head.x / 16.0F, (this.getParentModel()).head.y / 16.0F, (this.getParentModel()).head.z / 16.0F);
        headRoll = chilloo.getHeadRoll(tickDelta);
        matrices.mulPose(Vector3f.ZP.rotationDegrees(headRoll));
        matrices.mulPose(Vector3f.YP.rotationDegrees(headYaw));
        matrices.mulPose(Vector3f.XP.rotationDegrees(headPitch));
        matrices.mulPose(Vector3f.XN.rotationDegrees(90));
        matrices.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrices.translate(0F, -0.75F, 0.1408F);


        ItemStack itemStack = chilloo.getItemBySlot(EquipmentSlot.MAINHAND);
        this.heldItemRenderer.renderItem(chilloo, itemStack, ItemTransforms.TransformType.GROUND, false, matrices, vertexConsumers, light);
        matrices.popPose();
    }
}

