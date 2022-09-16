package com.ninni.frozenup.client.client.init;

import com.ninni.frozenup.client.model.ChillooEntityModel;
import com.ninni.frozenup.client.model.PenguinEntityModel;
import com.ninni.frozenup.client.model.ReindeerEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import static com.ninni.frozenup.FrozenUp.*;

@Environment(EnvType.CLIENT)
public class FrozenUpEntityModelLayers {
    public static final EntityModelLayer CHILLOO = main("chilloo", ChillooEntityModel::getTexturedModelData);
    public static final EntityModelLayer REINDEER = main("reindeer", ReindeerEntityModel::getTexturedModelData);
    public static final EntityModelLayer REINDEER_ARMOR = register("reindeer", "armor", ReindeerEntityModel::getTexturedModelData);
    public static final EntityModelLayer PENGUIN  = main("penguin", PenguinEntityModel::getTexturedModelData);

    private static EntityModelLayer register(String id, String name, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        EntityModelLayer layer = new EntityModelLayer(new Identifier(MOD_ID, id), name);
        EntityModelLayerRegistry.registerModelLayer(layer, provider);
        return layer;
    }

    private static EntityModelLayer main(String id, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        return register(id, "main", provider);
    }
}