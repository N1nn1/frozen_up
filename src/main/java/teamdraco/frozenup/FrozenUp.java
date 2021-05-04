package teamdraco.frozenup;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import teamdraco.frozenup.common.Compostables;
import teamdraco.frozenup.init.FrozenUpBlocks;
import teamdraco.frozenup.init.FrozenUpEntities;
import teamdraco.frozenup.init.FrozenUpItems;
import teamdraco.frozenup.init.FrozenUpSounds;

public class FrozenUp implements ModInitializer {
    public static final String MOD_ID = "frozenup";

    @Override
    public void onInitialize() {
        FrozenUpBlocks.init();
        FrozenUpItems.init();
        FrozenUpSounds.init();
        FrozenUpEntities.init();
        Compostables.registerCompostables();

        BiomeModifications.addSpawn(
                ctx -> ctx.getBiome().getCategory() == Biome.Category.ICY,
                SpawnGroup.CREATURE,
                FrozenUpEntities.CHILLOO,
                1 /* weight */,
                2 /* minGroupSize */,
                3 /* maxGroupSize */
        );
    }

    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(FrozenUp.MOD_ID, "frozenup_item_group"), () -> new ItemStack(FrozenUpItems.CHILLOO_FEATHER));
}