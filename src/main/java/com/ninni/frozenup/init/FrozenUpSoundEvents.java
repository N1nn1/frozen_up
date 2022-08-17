package com.ninni.frozenup.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.ninni.frozenup.FrozenUp.MOD_ID;

public class FrozenUpSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);

    public static final RegistryObject<SoundEvent> ENTITY_CHILLOO_AMBIENT = chilloo("ambient");
    public static final RegistryObject<SoundEvent> ENTITY_CHILLOO_DEATH = chilloo("death");
    public static final RegistryObject<SoundEvent> ENTITY_CHILLOO_HURT = chilloo("hurt");
    public static final RegistryObject<SoundEvent> ENTITY_CHILLOO_EAT = chilloo("eat");
    public static final RegistryObject<SoundEvent> ENTITY_CHILLOO_SPIT = chilloo("spit");
    private static RegistryObject<SoundEvent> chilloo(String type) { return entity("chilloo", type); }

    public static final RegistryObject<SoundEvent> BLOCK_CHILLOO_FEATHER_BLOCK_BREAK = chillooFeatherBlock("break");
    public static final RegistryObject<SoundEvent> BLOCK_CHILLOO_FEATHER_BLOCK_PLACE = chillooFeatherBlock("place");
    private static RegistryObject<SoundEvent> chillooFeatherBlock(String type) { return block("chilloo_feather_block", type); }

    public static final RegistryObject<SoundEvent> BLOCK_CHILLOO_FEATHER_LAMP_TOGGLE_ON = chillooFeatherLamp("toggle_on");
    public static final RegistryObject<SoundEvent> BLOCK_CHILLOO_FEATHER_LAMP_TOGGLE_OFF = chillooFeatherLamp("toggle_off");
    private static RegistryObject<SoundEvent> chillooFeatherLamp(String type) { return block("chilloo_feather_lamp", type); }

    public static final RegistryObject<SoundEvent> ITEM_MUG_DRINK = mug("drink");
    private static RegistryObject<SoundEvent> mug(String type) { return item("mug", type); }

    private static RegistryObject<SoundEvent> block(String block, String type) { return register("block." + block + "." + type); }
    private static RegistryObject<SoundEvent> entity(String entity, String type) { return register("entity." + entity + "." + type); }
    private static RegistryObject<SoundEvent> item(String item, String type) { return register("item." + item + "." + type); }

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(MOD_ID, name)));
    }
}
