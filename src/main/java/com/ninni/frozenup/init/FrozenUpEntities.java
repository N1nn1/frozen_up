package com.ninni.frozenup.init;

import com.ninni.frozenup.FrozenUp;
import com.ninni.frozenup.entity.ChillooEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = FrozenUp.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrozenUpEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FrozenUp.MOD_ID);

    public static final RegistryObject<EntityType<ChillooEntity>> CHILLOO = ENTITY_TYPE.register("chilloo", () -> EntityType.Builder.of(ChillooEntity::new, MobCategory.CREATURE).sized(1.2F, 1.4F).clientTrackingRange(8).build(new ResourceLocation(FrozenUp.MOD_ID, "chilloo").toString()));

}
