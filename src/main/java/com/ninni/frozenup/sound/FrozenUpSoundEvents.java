package com.ninni.frozenup.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.ninni.frozenup.FrozenUp.*;

public class FrozenUpSoundEvents {

    public static final SoundEvent ENTITY_CHILLOO_AMBIENT = chilloo("ambient");
    public static final SoundEvent ENTITY_CHILLOO_DEATH = chilloo("death");
    public static final SoundEvent ENTITY_CHILLOO_HURT = chilloo("hurt");
    public static final SoundEvent ENTITY_CHILLOO_EAT = chilloo("eat");
    public static final SoundEvent ENTITY_CHILLOO_SPIT = chilloo("spit");
    private static SoundEvent chilloo(String type) { return entity("chilloo", type); }

    public static final SoundEvent BLOCK_CHILLOO_FEATHER_BLOCK_BREAK = chillooFeatherBlock("break");
    public static final SoundEvent BLOCK_CHILLOO_FEATHER_BLOCK_PLACE = chillooFeatherBlock("place");
    private static SoundEvent chillooFeatherBlock(String type) { return block("chilloo_feather_block", type); }

    public static final SoundEvent BLOCK_CHILLOO_FEATHER_LAMP_TOGGLE_ON = chillooFeatherLamp("toggle_on");
    public static final SoundEvent BLOCK_CHILLOO_FEATHER_LAMP_TOGGLE_OFF = chillooFeatherLamp("toggle_off");
    private static SoundEvent chillooFeatherLamp(String type) { return block("chilloo_feather_lamp", type); }

    public static final SoundEvent ITEM_MUG_DRINK = mug("drink");
    private static SoundEvent mug(String type) { return item("mug", type); }

    private static SoundEvent block(String block, String type) { return register("block." + block + "." + type); }
    private static SoundEvent entity(String entity, String type) { return register("entity." + entity + "." + type); }
    private static SoundEvent item(String item, String type) { return register("item." + item + "." + type); }

    private static SoundEvent register(String name) {
        Identifier identifier = new Identifier(MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
    }
}
