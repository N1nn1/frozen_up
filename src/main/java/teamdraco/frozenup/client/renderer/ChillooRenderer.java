package teamdraco.frozenup.client.renderer;

import teamdraco.frozenup.FrozenUp;
import teamdraco.frozenup.client.model.ChillooModel;
import teamdraco.frozenup.entity.ChillooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class ChillooRenderer extends MobEntityRenderer<ChillooEntity, ChillooModel> {
	private static final Identifier TEXTURE = new Identifier(FrozenUp.MOD_ID, "textures/entity/chilloo.png");
    private static final Identifier[] COLOR_TEXTURES = new Identifier[32];

	public ChillooRenderer(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new ChillooModel(), 0.5F);
	}

    @Override
    public Identifier getTexture(ChillooEntity entity) {
        DyeColor bandColor = entity.getBandColor();
        if (bandColor == null) {
            DyeColor sweaterColor = entity.getSweaterColor();
            if (sweaterColor == null) return TEXTURE;
            return getTexture(sweaterColor.ordinal() + 16, color -> new Identifier(FrozenUp.MOD_ID, "textures/entity/sweaters/" + color.getName() + ".png"));
        } else {
            return getTexture(bandColor.ordinal(), color -> new Identifier(FrozenUp.MOD_ID, "textures/entity/bands/" + color.getName() + ".png"));
        }
	}

	private Identifier getTexture(int color, Function<DyeColor, Identifier> colorSupplier) {
        Identifier colorTexture = COLOR_TEXTURES[color];
        if (colorTexture == null) {
            colorTexture = colorSupplier.apply(DyeColor.byId(color % 16));
            COLOR_TEXTURES[color] = colorTexture;
        }
        return colorTexture;
    }
}
