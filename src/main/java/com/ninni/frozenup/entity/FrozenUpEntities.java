package com.ninni.frozenup.entity;

import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.FrozenUpClient;
import com.ninni.frozenup.FrozenUpTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class FrozenUpEntities {
    public static final EntityType<ChillooEntity> CHILLOO = register(
        "chilloo",
        FabricEntityTypeBuilder.createMob()
                               .entityFactory(ChillooEntity::new)
                               .defaultAttributes(ChillooEntity::createChillooAttributes)
                               .spawnGroup(SpawnGroup.CREATURE)
                               .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PathAwareEntity::canMobSpawn)
                               .dimensions(EntityDimensions.changing(0.9F, 0.9F))
                               .trackRangeChunks(8),
        new Pair<>(0xffffff, 0x32383c)
    );
    public static final EntityType<ReindeerEntity> REINDEER = register(
        "reindeer",
        FabricEntityTypeBuilder.createMob()
                               .entityFactory(ReindeerEntity::new)
                               .defaultAttributes(ReindeerEntity::createReindeerAttributes)
                               .spawnGroup(SpawnGroup.CREATURE)
                               .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.WORLD_SURFACE_WG, ReindeerEntity::canSpawn)
                               .dimensions(EntityDimensions.changing(1.3F, 1.6F))
                               .trackRangeChunks(8),
        new Pair<>( 0x5c392d, 0xdacabc )
    );
    public static final EntityType<PenguinEntity> PENGUIN = register(
        "penguin",
        FabricEntityTypeBuilder.createMob()
                               .entityFactory(PenguinEntity::new)
                               .defaultAttributes(PenguinEntity::createPenguinAttributes)
                               .spawnGroup(SpawnGroup.CREATURE)
                               .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.WORLD_SURFACE_WG, PenguinEntity::canSpawn)
                               .dimensions(EntityDimensions.changing(0.55F, 0.9F))
                               .trackRangeChunks(8),
        new Pair<>( 0x292929, 0xfff089 )
    );

    static {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS, BiomeKeys.SNOWY_TAIGA), SpawnGroup.CREATURE, FrozenUpEntities.CHILLOO, 20, 2, 6);
        BiomeModifications.addSpawn(BiomeSelectors.tag(FrozenUpTags.PENGUIN_SPAWNS_IN), SpawnGroup.CREATURE, FrozenUpEntities.PENGUIN, 5, 3, 8);
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.CLIMATE_COLD), SpawnGroup.CREATURE, FrozenUpEntities.REINDEER, 5, 1, 2);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> entityType, Pair<Integer, Integer> spawnEggColors) {
        EntityType<T> builtEntityType = entityType.build();

        if (spawnEggColors != null) {
            Registry.register(
                Registry.ITEM, new Identifier(FrozenUp.MOD_ID, id + "_spawn_egg"),
                new SpawnEggItem(
                    (EntityType<? extends MobEntity>) builtEntityType,
                    spawnEggColors.getLeft(), spawnEggColors.getRight(),
                    new FabricItemSettings().maxCount(64).group(FrozenUp.ITEM_GROUP)
                )
            );
        }

        return Registry.register(Registry.ENTITY_TYPE, new Identifier(FrozenUp.MOD_ID, id), builtEntityType);
    }

    @Environment(EnvType.CLIENT) public static Identifier texture(String path) { return FrozenUpClient.texture("entity/" + path); }
}
