package net.teamdraco.frozenup.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.teamdraco.frozenup.FrozenUp;

public class FrozenUpSoundEvents {
    public static final SoundEvent ENTITY_CHILLOO_AMBIENT = bouncer("ambient");
    public static final SoundEvent ENTITY_CHILLOO_DEATH = bouncer("death");
    public static final SoundEvent ENTITY_CHILLOO_HURT = bouncer("hurt");
    public static final SoundEvent ENTITY_CHILLOO_EAT = bouncer("eat");
    public static final SoundEvent ENTITY_CHILLOO_SPIT = bouncer("spit");
    private static SoundEvent bouncer(String type) {
        return entity("chilloo", type);
    }

    public static final SoundEvent BLOCK_CHILLOO_FEATHER_BLOCK_BREAK = chillooFeatherBlock("break");
    public static final SoundEvent BLOCK_CHILLOO_FEATHER_BLOCK_PLACE = chillooFeatherBlock("place");
    private static SoundEvent chillooFeatherBlock(String type) {
        return block("chilloo_feather_block", type);
    }

    public static final SoundEvent BLOCK_CHILLOO_FEATHER_LAMP_TOGGLE_ON = chillooFeatherLamp("toggle_on");
    public static final SoundEvent BLOCK_CHILLOO_FEATHER_LAMP_TOGGLE_OFF = chillooFeatherLamp("toggle_off");
    private static SoundEvent chillooFeatherLamp(String type) {
        return block("chilloo_feather_lamp", type);
    }

    private static SoundEvent block(String block, String type) {
        return register("block." + block + "." + type);
    }
    private static SoundEvent entity(String entity, String type) {
        return register("entity." + entity + "." + type);
    }
    private static SoundEvent ambient(String id, String type) {
        return register("ambient." + type + "." + id);
    }

    private static SoundEvent register(String name) {
        Identifier id = new Identifier(FrozenUp.MOD_ID, name);
        SoundEvent event = new SoundEvent(id);
        Registry.register(Registry.SOUND_EVENT, id, event);
        return event;
    }
}
