package com.ninni.frozenup.client.renderer;

import com.ninni.frozenup.client.client.init.FrozenUpEntityModelLayers;
import com.ninni.frozenup.client.model.PenguinEntityModel;
import com.ninni.frozenup.entity.PenguinEntity;
import com.ninni.frozenup.entity.PenguinMood;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import static com.ninni.frozenup.FrozenUp.*;

@Environment(EnvType.CLIENT)
public class PenguinEntityRenderer<T extends LivingEntity> extends MobEntityRenderer<PenguinEntity, PenguinEntityModel<PenguinEntity>> {
    public static final Identifier HAPPY_TEXTURE    = new Identifier(MOD_ID, "textures/entity/penguin/penguin_happy.png");
    public static final Identifier CONFUSED_TEXTURE = new Identifier(MOD_ID, "textures/entity/penguin/penguin_confused.png");
    public static final Identifier FOCUSED_TEXTURE  = new Identifier(MOD_ID, "textures/entity/penguin/penguin_focused.png");
    public static final Identifier AGITATED_TEXTURE = new Identifier(MOD_ID, "textures/entity/penguin/penguin_agitated.png");

    public static final Identifier BABY_HAPPY_TEXTURE    = new Identifier(MOD_ID, "textures/entity/penguin/hatchling/penguin_happy.png");
    public static final Identifier BABY_CONFUSED_TEXTURE = new Identifier(MOD_ID, "textures/entity/penguin/hatchling/penguin_confused.png");
    public static final Identifier BABY_FOCUSED_TEXTURE  = new Identifier(MOD_ID, "textures/entity/penguin/hatchling/penguin_focused.png");
    public static final Identifier BABY_AGITATED_TEXTURE = new Identifier(MOD_ID, "textures/entity/penguin/hatchling/penguin_agitated.png");

    public PenguinEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PenguinEntityModel<>(ctx.getPart(FrozenUpEntityModelLayers.PENGUIN)), 0.3F);
    }

    @Override
    protected void setupTransforms(PenguinEntity penguin, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(penguin, matrixStack, f, g, h);
        if (penguin.isTouchingWater() && !penguin.hasEgg()) {
            matrixStack.translate(0, 0.2, 0);
        }
    }

    @Override
    public Identifier getTexture(PenguinEntity entity) {
        PenguinMood mood = entity.getMood();
        if (entity.isBaby()) {
            return switch (mood) {
                case AGITATED -> BABY_AGITATED_TEXTURE;
                case FOCUSED -> BABY_FOCUSED_TEXTURE;
                case CONFUSED -> BABY_CONFUSED_TEXTURE;
                default -> BABY_HAPPY_TEXTURE;
            };
        } else
            return switch (mood) {
            case AGITATED -> AGITATED_TEXTURE;
            case FOCUSED -> FOCUSED_TEXTURE;
            case CONFUSED -> CONFUSED_TEXTURE;
            default -> HAPPY_TEXTURE;
        };
    }
}

