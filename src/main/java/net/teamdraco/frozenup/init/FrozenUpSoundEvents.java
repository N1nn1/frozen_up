package net.teamdraco.frozenup.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.teamdraco.frozenup.FrozenUp;

public class FrozenUpSoundEvents {
    public static final SoundEvent ENTITY_CHILLOO_AMBIENT = bouncer("ambient");
    public static final SoundEvent ENTITY_CHILLOO_DEATH = bouncer("death");
    public static final SoundEvent ENTITY_CHILLOO_HURT = bouncer("hurt");
    private static SoundEvent bouncer(String type) {
        return entity("chilloo", type);
    }

    private static SoundEvent entity(String entity, String type) {
        return register("entity." + entity + "." + type);
    }

    private static SoundEvent register(String name) {
        Identifier id = new Identifier(FrozenUp.MOD_ID, name);
        SoundEvent event = new SoundEvent(id);
        Registry.register(Registry.SOUND_EVENT, id, event);
        return event;
    }
}
