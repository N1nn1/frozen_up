package net.teamdraco.frozenup.client.client.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.teamdraco.frozenup.FrozenUp;
import net.teamdraco.frozenup.mixin.client.EntityModelLayersInvoker;

@Environment(EnvType.CLIENT)
public class FrozenUpEntityModelLayers {
    public static final EntityModelLayer CHILLOO = registerMain("chilloo");
    public static final EntityModelLayer PRESERVED = registerMain("preserved");
    public static final EntityModelLayer PRESERVED_INNER_ARMOR = createInnerArmor("preserved_inner_armor");
    public static final EntityModelLayer PRESERVED_OUTER_ARMOR = createOuterArmor("preserved_outer_armor");

    private static EntityModelLayer registerMain(String id) {
        return EntityModelLayersInvoker.register(new Identifier(FrozenUp.MOD_ID, id).toString(), "main");
    }

    private static EntityModelLayer createInnerArmor(String id) {
        return EntityModelLayersInvoker.register(new Identifier(FrozenUp.MOD_ID, id).toString(), "inner_armor");
    }

    private static EntityModelLayer createOuterArmor(String id) {
        return EntityModelLayersInvoker.register(new Identifier(FrozenUp.MOD_ID, id).toString(), "outer_armor");
    }
}