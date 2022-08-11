package com.ninni.frozenup.entity;

import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.FrozenUpClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
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

public class FrozenUpEntities {
    public static final EntityType<ChillooEntity> CHILLOO = register(
        "chilloo",
        FabricEntityTypeBuilder.createMob()
                               .entityFactory(ChillooEntity::new)
                               .defaultAttributes(ChillooEntity::createChillooAttributes)
                               .spawnGroup(SpawnGroup.CREATURE)
                               .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PathAwareEntity::canMobSpawn)
                               .dimensions(EntityDimensions.changing(1.2f, 1.4f))
                               .trackRangeBlocks(8),
        new Pair<>(0xffffff, 0x32383c)
    );

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
