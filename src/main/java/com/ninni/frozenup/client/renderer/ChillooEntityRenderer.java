package com.ninni.frozenup.client.renderer;

import com.ninni.frozenup.client.client.init.FrozenUpEntityModelLayers;
import com.ninni.frozenup.client.model.ChillooEntityModel;
import com.ninni.frozenup.client.renderer.entity.feature.ChillooBandsFeatureRenderer;
import com.ninni.frozenup.client.renderer.entity.feature.ChillooHeldItemFeatureRenderer;
import com.ninni.frozenup.entity.ChillooEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

import static com.ninni.frozenup.FrozenUp.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class ChillooEntityRenderer extends MobRenderer<ChillooEntity, ChillooEntityModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, "textures/entity/chilloo/chilloo.png");
    private static final ResourceLocation TAMED_TEXTURE = new ResourceLocation(MOD_ID, "textures/entity/chilloo/chilloo_tamed.png");
    private static final ResourceLocation SHEARED_TEXTURE = new ResourceLocation(MOD_ID, "textures/entity/chilloo/chilloo_sheared.png");
    private static final ResourceLocation TAMED_SHEARED_TEXTURE = new ResourceLocation(MOD_ID, "textures/entity/chilloo/chilloo_tamed_sheared.png");

    public ChillooEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new ChillooEntityModel(ctx.bakeLayer(FrozenUpEntityModelLayers.CHILLOO)), 0.6F);
        this.addLayer(new ChillooBandsFeatureRenderer(this));
        this.addLayer(new ChillooHeldItemFeatureRenderer(this, ctx.getItemInHandRenderer()));
    }

    @Override
    protected boolean isShaking(ChillooEntity entity) {
        if (entity.isSheared() && entity.level().getBiome(entity.blockPosition()).is(Tags.Biomes.IS_COLD)) return true;
        return super.isShaking(entity);
    }

    @Override public ResourceLocation getTextureLocation(ChillooEntity entity) {
        if (entity.isSheared()) return entity.isTame() ? TAMED_SHEARED_TEXTURE : SHEARED_TEXTURE;
        else return entity.isTame() ? TAMED_TEXTURE : TEXTURE;
    }

}
