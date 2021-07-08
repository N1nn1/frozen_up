package teamdraco.frozenup;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import teamdraco.frozenup.init.FrozenUpBlocks;
import teamdraco.frozenup.init.FrozenUpEntities;
import teamdraco.frozenup.init.FrozenUpItems;

public class FrozenUp implements ModInitializer {
    public static final String MOD_ID = "frozenup";
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(FrozenUp.MOD_ID, "item_group"), () -> new ItemStack(FrozenUpItems.CHILLOO_FEATHER));

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        Reflection.initialize(
                FrozenUpEntities.class,
                FrozenUpBlocks.class,
                FrozenUpItems.class
        );

    }
}
