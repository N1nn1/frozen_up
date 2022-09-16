package com.ninni.frozenup.client.renderer.entity.feature;

import com.ninni.frozenup.client.model.ChillooEntityModel;
import com.ninni.frozenup.client.renderer.ChillooEntityRenderer;
import com.ninni.frozenup.entity.ChillooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class ChillooHeldItemFeatureRenderer extends FeatureRenderer<ChillooEntity, ChillooEntityModel> {
    private final HeldItemRenderer heldItemRenderer;

    public <T extends LivingEntity> ChillooHeldItemFeatureRenderer(ChillooEntityRenderer context, HeldItemRenderer heldItemRenderer) {
        super(context);
        this.heldItemRenderer = heldItemRenderer;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ChillooEntity chilloo, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        boolean baby = chilloo.isBaby();

        matrices.push();
        float headRoll;
        if (baby) {
            matrices.scale(0.75F, 0.75F, 0.75F);
            matrices.translate(0.0, 0.5, 0.2);
        }
        matrices.translate(this.getContextModel().head.pivotX / 16.0F, (this.getContextModel()).head.pivotY / 16.0F, (this.getContextModel()).head.pivotZ / 16.0F);
        headRoll = chilloo.getHeadRoll(tickDelta);
        matrices.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(headRoll));
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(headYaw));
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(headPitch));
        matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(90));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180));
        matrices.translate(0F, -0.75F, 0.1408F);


        ItemStack itemStack = chilloo.getEquippedStack(EquipmentSlot.MAINHAND);
        this.heldItemRenderer.renderItem(chilloo, itemStack, ModelTransformation.Mode.GROUND, false, matrices, vertexConsumers, light);
        matrices.pop();
    }
}

