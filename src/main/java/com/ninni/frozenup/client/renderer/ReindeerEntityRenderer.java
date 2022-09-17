package com.ninni.frozenup.client.renderer;

import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.client.client.init.FrozenUpEntityModelLayers;
import com.ninni.frozenup.client.model.ReindeerEntityModel;
import com.ninni.frozenup.client.renderer.entity.feature.ReindeerArmorFeatureRenderer;
import com.ninni.frozenup.client.renderer.entity.feature.ReindeerFestiveOverlayFeatureRenderer;
import com.ninni.frozenup.entity.ReindeerEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Calendar;

@OnlyIn(Dist.CLIENT)
public class ReindeerEntityRenderer<T extends LivingEntity> extends MobRenderer<ReindeerEntity, ReindeerEntityModel<ReindeerEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(FrozenUp.MOD_ID, "textures/entity/reindeer/reindeer.png");
    public static final ResourceLocation TEXTURE_FESTIVE = new ResourceLocation(FrozenUp.MOD_ID, "textures/entity/reindeer/reindeer_festive.png");
    public static final boolean FESTIVE = Util.make(() -> {
        Calendar calendar = Calendar.getInstance();
        return (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26)
                || (calendar.get(Calendar.MONTH) + 1 == 8 && calendar.get(Calendar.DATE) == 2);
    });

    public ReindeerEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new ReindeerEntityModel<>(ctx.bakeLayer(FrozenUpEntityModelLayers.REINDEER)), 0.6F);
        this.addLayer(new ReindeerArmorFeatureRenderer<>(this, new ReindeerEntityModel<>(ctx.bakeLayer(FrozenUpEntityModelLayers.REINDEER_ARMOR)), new ResourceLocation(FrozenUp.MOD_ID, "textures/entity/reindeer/reindeer_armor.png")));
        this.addLayer(new SaddleLayer<>(this, new ReindeerEntityModel<>(ctx.bakeLayer(FrozenUpEntityModelLayers.REINDEER_ARMOR)), new ResourceLocation(FrozenUp.MOD_ID, "textures/entity/reindeer/reindeer_saddle.png")));
        this.addLayer(new ReindeerFestiveOverlayFeatureRenderer(this));
    }

    public static boolean isFestive(ReindeerEntity entity) {
        if (entity.hasCustomName()) {
            String string = ChatFormatting.stripFormatting(entity.getName().getString());
            if ("rudolph".equalsIgnoreCase(string)) return true;
        }

        return FESTIVE;
    }

    @Override
    public ResourceLocation getTextureLocation(ReindeerEntity entity) {
        return isFestive(entity) ? TEXTURE_FESTIVE : TEXTURE;
    }
}
