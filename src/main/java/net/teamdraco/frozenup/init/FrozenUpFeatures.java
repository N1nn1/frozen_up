package net.teamdraco.frozenup.init;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.teamdraco.frozenup.FrozenUp;
import net.teamdraco.frozenup.world.gen.feature.IceTreeFeature;
import net.teamdraco.frozenup.world.gen.feature.IcicleFeature;

public class FrozenUpFeatures {
    public static final Feature<DefaultFeatureConfig> ICICLE = register("icicle", new IcicleFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> ICE_TREE = register("ice_tree", new IceTreeFeature(DefaultFeatureConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String id, F feature) {
        return Registry.register(Registry.FEATURE, new Identifier(FrozenUp.MOD_ID, id), feature);
    }
}
