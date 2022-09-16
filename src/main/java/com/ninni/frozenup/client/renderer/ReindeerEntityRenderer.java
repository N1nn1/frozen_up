package com.ninni.frozenup.client.renderer;

import com.ninni.frozenup.client.client.init.FrozenUpEntityModelLayers;
import com.ninni.frozenup.client.model.ReindeerEntityModel;
import com.ninni.frozenup.client.renderer.entity.feature.ReindeerArmorFeatureRenderer;
import com.ninni.frozenup.client.renderer.entity.feature.ReindeerFestiveOverlayFeatureRenderer;
import com.ninni.frozenup.entity.ReindeerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Calendar;

import static com.ninni.frozenup.FrozenUp.*;

@Environment(EnvType.CLIENT)
public class ReindeerEntityRenderer<T extends LivingEntity> extends MobEntityRenderer<ReindeerEntity, ReindeerEntityModel<ReindeerEntity>> {
    public static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/entity/reindeer/reindeer.png");
    public static final Identifier TEXTURE_FESTIVE = new Identifier(MOD_ID, "textures/entity/reindeer/reindeer_festive.png");
    public static final boolean FESTIVE = Util.make(() -> {
        //checks if it's Christmas or if it's the world reindeer day to apply a custom skin
        Calendar calendar = Calendar.getInstance();
        return (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26)
            || (calendar.get(Calendar.MONTH) + 1 == 8 && calendar.get(Calendar.DATE) == 2);
    });

    public ReindeerEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ReindeerEntityModel<>(ctx.getPart(FrozenUpEntityModelLayers.REINDEER)), 0.6F);
        this.addFeature(new ReindeerArmorFeatureRenderer<>(this, new ReindeerEntityModel<>(ctx.getPart(FrozenUpEntityModelLayers.REINDEER_ARMOR)), new Identifier(MOD_ID, "textures/entity/reindeer/reindeer_armor.png")));
        this.addFeature(new SaddleFeatureRenderer<>(this, new ReindeerEntityModel<>(ctx.getPart(FrozenUpEntityModelLayers.REINDEER_ARMOR)), new Identifier(MOD_ID, "textures/entity/reindeer/reindeer_saddle.png")));
        this.addFeature(new ReindeerFestiveOverlayFeatureRenderer(this));
    }

    public static boolean isFestive(ReindeerEntity entity) {
        if (entity.hasCustomName()) {
            String string = Formatting.strip(entity.getName().getString());
            if ("rudolph".equalsIgnoreCase(string)) return true;
        }

        return FESTIVE;
    }

    @Override
    public Identifier getTexture(ReindeerEntity entity) {
        return isFestive(entity) ? TEXTURE_FESTIVE : TEXTURE;
    }
}

