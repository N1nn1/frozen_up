package net.teamdraco.frozenup.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import net.teamdraco.frozenup.init.FrozenUpEntities;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class PreservedEntityRenderer extends ZombieEntityRenderer {
    private static final Identifier TEXTURE = FrozenUpEntities.texture("zombie/preserved");

    @SuppressWarnings("unused")
    public PreservedEntityRenderer(EntityRenderDispatcher dispatcher, @Nullable EntityRendererRegistry.Context ctx) {
        super(dispatcher);
    }

    public Identifier getTexture(ZombieEntity zombieEntity) {
        return TEXTURE;
    }
}
