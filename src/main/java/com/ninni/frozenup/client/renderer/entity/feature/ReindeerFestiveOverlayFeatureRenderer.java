package com.ninni.frozenup.client.renderer.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.client.model.ReindeerEntityModel;
import com.ninni.frozenup.client.renderer.ReindeerEntityRenderer;
import com.ninni.frozenup.entity.ReindeerEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ReindeerFestiveOverlayFeatureRenderer extends EyesLayer<ReindeerEntity, ReindeerEntityModel<ReindeerEntity>> {
    private static final RenderType OVERLAY = RenderType.eyes(new ResourceLocation(FrozenUp.MOD_ID, "textures/entity/reindeer/reindeer_festive_overlay.png"));

    public ReindeerFestiveOverlayFeatureRenderer(RenderLayerParent<ReindeerEntity, ReindeerEntityModel<ReindeerEntity>> context) { super(context); }

    @Override
    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, ReindeerEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (ReindeerEntityRenderer.isFestive(pLivingEntity)) super.render(pMatrixStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks, pAgeInTicks, pNetHeadYaw, pHeadPitch);
    }

    @Override
    public RenderType renderType() {
        return OVERLAY;
    }

}
