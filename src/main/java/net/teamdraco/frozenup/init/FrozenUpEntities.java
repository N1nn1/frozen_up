package net.teamdraco.frozenup.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.teamdraco.frozenup.FrozenUp;
import net.teamdraco.frozenup.FrozenUpClient;
import net.teamdraco.frozenup.entity.ChillooEntity;

@SuppressWarnings("deprecation")
public class FrozenUpEntities {
    public static final EntityType<ChillooEntity> CHILLOO = register(
        "chilloo",
        FabricEntityTypeBuilder.createMob()
                               .entityFactory(ChillooEntity::new)
                               .defaultAttributes(ChillooEntity::createChillooAttributes)
                               .dimensions(EntityDimensions.changing(1.0f, 1.0f))
                               .spawnGroup(SpawnGroup.CREATURE)
                               .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PathAwareEntity::canMobSpawn),
        new Pair<>(0xc2cbce, 0x32383c)
    );

    static {
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.ICY).and(ctx -> ctx.getBiome().getTemperature() <= 0.0f), SpawnGroup.CREATURE, FrozenUpEntities.CHILLOO, 1, 2, 3);
    }

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> entityType, Pair<Integer, Integer> spawnEggColors) {
        EntityType<T> builtEntityType = entityType.build();

        if (spawnEggColors != null) {
            Registry.register(Registry.ITEM, new Identifier(FrozenUp.MOD_ID, id + "_spawn_egg"), new SpawnEggItem(builtEntityType, spawnEggColors.getLeft(), spawnEggColors.getRight(), new FabricItemSettings().maxCount(64).group(FrozenUp.ITEM_GROUP)));
        }

        return Registry.register(Registry.ENTITY_TYPE, new Identifier(FrozenUp.MOD_ID, id), builtEntityType);
    }

    @Environment(EnvType.CLIENT)
    public static Identifier texture(String path) {
        return FrozenUpClient.texture("entity/" + path);
    }
}
