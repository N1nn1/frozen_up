package com.ninni.frozenup.events;

import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.entity.ChillooEntity;
import com.ninni.frozenup.init.FrozenUpEntities;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FrozenUp.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEvents {

    @SubscribeEvent
    public static void onEntityAttributeInit(EntityAttributeCreationEvent event) {
        SpawnPlacements.register(FrozenUpEntities.CHILLOO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PathfinderMob::checkMobSpawnRules);
        event.put(FrozenUpEntities.CHILLOO.get(), ChillooEntity.createChillooAttributes().build());
    }

}
