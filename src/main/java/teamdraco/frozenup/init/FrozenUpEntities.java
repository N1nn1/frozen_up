package teamdraco.frozenup.init;

import teamdraco.frozenup.FrozenUp;
import teamdraco.frozenup.entity.ChillooEntity;
import net.fabricmc.fabric.api.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FrozenUpEntities {
    public static final EntityType<ChillooEntity> CHILLOO = Registry.register(Registry.ENTITY_TYPE, new Identifier(FrozenUp.MOD_ID, "chilloo"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChillooEntity::new).dimensions(EntityDimensions.fixed(1.0f, 1.0f)).build());

    public static void init(){
        FabricDefaultAttributeRegistry.register(FrozenUpEntities.CHILLOO, ChillooEntity.createMobAttributes());
    }
}