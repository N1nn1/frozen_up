package net.teamdraco.frozenup.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.teamdraco.frozenup.FrozenUp;
import net.teamdraco.frozenup.client.model.ChillooEntityModel;
import net.teamdraco.frozenup.entity.ChillooEntity;
import net.teamdraco.frozenup.init.FrozenUpEntities;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class ChillooEntityRenderer extends MobEntityRenderer<ChillooEntity, ChillooEntityModel> {
    private static final Identifier TEXTURE = FrozenUpEntities.texture("chilloo/chilloo");
    private static final Identifier[] COLOR_TEXTURES = new Identifier[32];

    @SuppressWarnings("unused")
    public ChillooEntityRenderer(EntityRenderDispatcher dispatcher, @Nullable EntityRendererRegistry.Context ctx) {
        super(dispatcher, new ChillooEntityModel(), 0.5F);
    }

    @Override
    public Identifier getTexture(ChillooEntity entity) {
        DyeColor bandColor = entity.getBandColor();
        if (bandColor == null) {
            DyeColor sweaterColor = entity.getSweaterColor();
            if (sweaterColor == null)
                return TEXTURE;
            return getTexture(sweaterColor.ordinal() + 16, color -> new Identifier(FrozenUp.MOD_ID, "textures/entity/chilloo/sweaters/" + color.getName() + ".png"));
        } else {
            return getTexture(bandColor.ordinal(), color -> new Identifier(FrozenUp.MOD_ID, "textures/entity/chilloo/bands/" + color.getName() + ".png"));
        }
    }

    private Identifier getTexture(int colorId, Function<DyeColor, Identifier> colorSupplier) {
        Identifier texture = COLOR_TEXTURES[colorId];
        if (texture == null) {
            texture = colorSupplier.apply(DyeColor.byId(colorId % 16));
            COLOR_TEXTURES[colorId] = texture;
        }

        return texture;
    }
}
