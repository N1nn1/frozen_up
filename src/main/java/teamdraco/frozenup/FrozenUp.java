package teamdraco.frozenup;

import teamdraco.frozenup.common.Compostables;
import teamdraco.frozenup.init.FrozenUpBlocks;
import teamdraco.frozenup.init.FrozenUpEntities;
import teamdraco.frozenup.init.FrozenUpItems;
import teamdraco.frozenup.init.FrozenUpSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class FrozenUp implements ModInitializer {
    public static final String MOD_ID = "frozenup";

    @Override
    public void onInitialize() {
        FrozenUpBlocks.init();
        FrozenUpItems.init();
        FrozenUpSounds.init();
        FrozenUpEntities.init();
        Compostables.registerCompostables();
    }

    /*
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerBiomes(BiomeLoadingEvent event) {
        Biome.Weather climate = event.getClimate();
        switch (event.getCategory()) {
            case ICY:
                float temperature = climate.temperature;
                if (climate.temperature <= 0.0f) {
                    event.getSpawns().getSpawner(SpawnGroup.CREATURE).add(new SpawnSettings.SpawnEntry(FrozenUpEntities.CHILLOO, 1, 2, 3));
                }
                break;
        }
    }

    private void registerCommon(FMLCommonSetupEvent event) {
    	CommonEvents.setup();
        registerEntityAttributes();
        SpawnRestriction.register(FrozenUpEntities.CHILLOO, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
    }

    private void registerEntityAttributes() {
        DefaultAttributeRegistry.put(FrozenUpEntities.CHILLOO, ChillooEntity.createAttributes().build());
    }
    */

    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(FrozenUp.MOD_ID, "frozenup_item_group"), () -> new ItemStack(FrozenUpItems.CHILLOO_FEATHER));
}