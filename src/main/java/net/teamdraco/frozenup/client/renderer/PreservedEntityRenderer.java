package net.teamdraco.frozenup.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import net.teamdraco.frozenup.client.client.init.FrozenUpEntityModelLayers;
import net.teamdraco.frozenup.init.FrozenUpEntities;

@Environment(EnvType.CLIENT)
public class PreservedEntityRenderer extends ZombieEntityRenderer {
    private static final Identifier TEXTURE = FrozenUpEntities.texture("zombie/preserved");

    @SuppressWarnings("unused")
    public PreservedEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, FrozenUpEntityModelLayers.PRESERVED, FrozenUpEntityModelLayers.PRESERVED_INNER_ARMOR, FrozenUpEntityModelLayers.PRESERVED_OUTER_ARMOR);
    }

    @Override
    public Identifier getTexture(ZombieEntity zombieEntity) {
        return TEXTURE;
    }
}
