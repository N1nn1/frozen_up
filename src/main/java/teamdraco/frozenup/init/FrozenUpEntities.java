package teamdraco.frozenup.init;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import teamdraco.frozenup.FrozenUp;
import teamdraco.frozenup.entity.ChillooEntity;

public class FrozenUpEntities {
    public static final EntityType<ChillooEntity> CHILLOO = registerEntity("chilloo", FabricEntityTypeBuilder.createMob().spawnGroup(SpawnGroup.CREATURE).entityFactory(ChillooEntity::new).dimensions(EntityDimensions.changing(1.0f, 1.0f)).defaultAttributes(ChillooEntity::createChillooAttributes).build());

    public static void init() {
    }

    public static <T extends Entity> EntityType<T> registerEntity(String path, EntityType<T> entity) {
    	return Registry.register(Registry.ENTITY_TYPE, new Identifier(FrozenUp.MOD_ID, path), entity);
    }
}
