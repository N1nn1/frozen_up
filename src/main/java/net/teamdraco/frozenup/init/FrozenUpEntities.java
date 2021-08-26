package net.teamdraco.frozenup.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
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
import net.teamdraco.frozenup.entity.PreservedEntity;

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
    public static final EntityType<PreservedEntity> PRESERVED = register(
            "preserved",
            FabricEntityTypeBuilder.createMob()
                    .entityFactory(PreservedEntity::new)
                    .defaultAttributes(PreservedEntity::createZombieAttributes)
                    .dimensions(EntityDimensions.changing(0.6f, 1.95f))
                    .spawnGroup(SpawnGroup.MONSTER)
                    .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PreservedEntity::canSpawn),
            new Pair<>(0x8fae7d, 0x372f42)
    );

    static {
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.ICY).and(ctx -> ctx.getBiome().getTemperature() <= 0.0f), SpawnGroup.CREATURE, FrozenUpEntities.CHILLOO, 30, 3, 6);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.ICY).and(ctx -> ctx.getBiome().getTemperature() <= 0.0f), SpawnGroup.MONSTER, FrozenUpEntities.PRESERVED, 60, 1, 1);
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

    @Environment(EnvType.CLIENT)
    public static Identifier texture(String path) {
        return FrozenUpClient.texture("entity/" + path);
    }
}
