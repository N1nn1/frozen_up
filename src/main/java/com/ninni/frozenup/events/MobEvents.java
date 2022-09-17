package com.ninni.frozenup.events;

import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.entity.ChillooEntity;
import com.ninni.frozenup.entity.PenguinEntity;
import com.ninni.frozenup.entity.ReindeerEntity;
import com.ninni.frozenup.init.FrozenUpEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FrozenUp.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEvents {

    @SubscribeEvent
    public static void onEntityAttributeInit(EntityAttributeCreationEvent event) {
        SpawnPlacements.register(FrozenUpEntities.CHILLOO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PathfinderMob::checkMobSpawnRules);
        SpawnPlacements.register(FrozenUpEntities.PENGUIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE_WG, PenguinEntity::canSpawn);
        SpawnPlacements.register(FrozenUpEntities.REINDEER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE_WG, ReindeerEntity::canSpawn);

        event.put(FrozenUpEntities.PENGUIN.get(), PenguinEntity.createPenguinAttributes().build());
        event.put(FrozenUpEntities.REINDEER.get(), ReindeerEntity.createReindeerAttributes().build());
        event.put(FrozenUpEntities.CHILLOO.get(), ChillooEntity.createChillooAttributes().build());
    }

    @SubscribeEvent
    public void onEntityJoinWorldEvent(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Wolf wolf) {
            wolf.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(wolf, ReindeerEntity.class, false));
        }
    }

}
