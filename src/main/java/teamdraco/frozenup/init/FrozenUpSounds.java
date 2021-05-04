package teamdraco.frozenup.init;

import teamdraco.frozenup.FrozenUp;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FrozenUpSounds {
    public static SoundEvent CHILLOO_AMBIENT;
    public static SoundEvent CHILLOO_HURT;
    public static SoundEvent CHILLOO_DEATH;

    public static SoundEvent registerSounds(Identifier id, SoundEvent soundEvent) {
       return Registry.register(Registry.SOUND_EVENT, id, soundEvent);
    }

    public static void init(){
        CHILLOO_AMBIENT = FrozenUpSounds.registerSounds(new Identifier(FrozenUp.MOD_ID, "chilloo.ambient"), new SoundEvent(new Identifier(FrozenUp.MOD_ID, "chilloo.ambient")));
        CHILLOO_HURT = FrozenUpSounds.registerSounds(new Identifier(FrozenUp.MOD_ID, "chilloo.hurt"), new SoundEvent(new Identifier(FrozenUp.MOD_ID, "chilloo.hurt")));
        CHILLOO_DEATH = FrozenUpSounds.registerSounds(new Identifier(FrozenUp.MOD_ID, "chilloo.death"), new SoundEvent(new Identifier(FrozenUp.MOD_ID, "chilloo.death")));
    }
}