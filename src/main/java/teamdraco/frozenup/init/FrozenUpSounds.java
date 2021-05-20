package teamdraco.frozenup.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import teamdraco.frozenup.FrozenUp;

public class FrozenUpSounds {
    public static SoundEvent CHILLOO_AMBIENT;
    public static SoundEvent CHILLOO_HURT;
    public static SoundEvent CHILLOO_DEATH;

    public static void init() {
        CHILLOO_AMBIENT = registerSound("chilloo.ambient");
        CHILLOO_HURT = registerSound("chilloo.hurt");
        CHILLOO_DEATH = registerSound("chilloo.death");
    }

    public static SoundEvent registerSound(String path) {
        Identifier id = new Identifier(FrozenUp.MOD_ID, path);
        return registerSound(id, new SoundEvent(id));
    }

    public static SoundEvent registerSound(Identifier id, SoundEvent soundEvent) {
       return Registry.register(Registry.SOUND_EVENT, id, soundEvent);
    }
}
