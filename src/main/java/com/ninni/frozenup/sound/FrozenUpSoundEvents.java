package com.ninni.frozenup.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

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

    public static final SoundEvent ITEM_PINECONE_EAT = pinecone("eat");
    private static SoundEvent pinecone(String type) {
        return item("pinecone", type);
    }

    public static final SoundEvent BLOCK_COMPACTED_SNOW_BREAK = compacted_snow("break");
    public static final SoundEvent BLOCK_COMPACTED_SNOW_STEP  = compacted_snow("step");
    public static final SoundEvent BLOCK_COMPACTED_SNOW_PLACE = compacted_snow("place");
    public static final SoundEvent BLOCK_COMPACTED_SNOW_HIT   = compacted_snow("hit");
    public static final SoundEvent BLOCK_COMPACTED_SNOW_FALL  = compacted_snow("fall");
    private static SoundEvent compacted_snow(String type) {
        return block("compacted_snow", type);
    }

    public static final SoundEvent ENTITY_REINDEER_GALLOP  = reindeer("gallop");
    public static final SoundEvent ENTITY_REINDEER_BREATHE = reindeer("breathe");
    public static final SoundEvent ENTITY_REINDEER_AMBIENT = reindeer("idle");
    public static final SoundEvent ENTITY_REINDEER_HURT    = reindeer("hurt");
    public static final SoundEvent ENTITY_REINDEER_DEATH   = reindeer("death");
    public static final SoundEvent ENTITY_REINDEER_ANGRY   = reindeer("angry");
    public static final SoundEvent ENTITY_REINDEER_EAT     = reindeer("eat");
    public static final SoundEvent ENTITY_REINDEER_JUMP    = reindeer("jump");
    public static final SoundEvent ENTITY_REINDEER_CLOUD_JUMP    = reindeer("cloud_jump");
    private static SoundEvent reindeer(String type) {
        return entity("reindeer", type);
    }

    public static final SoundEvent ENTITY_PENGUIN_AMBIENT   = penguin("idle");
    public static final SoundEvent ENTITY_PENGUIN_HURT      = penguin("hurt");
    public static final SoundEvent ENTITY_PENGUIN_DEATH     = penguin("death");
    public static final SoundEvent ENTITY_PENGUIN_EGG_CRACK = penguin("crack");
    public static final SoundEvent ENTITY_PENGUIN_HATCH     = penguin("hatch");
    public static final SoundEvent ENTITY_PENGUIN_SLIDE     = penguin("slide");
    public static final SoundEvent ENTITY_PENGUIN_BOOM      = penguin("boom");
    private static SoundEvent penguin(String type) {
        return entity("penguin", type);
    }

    private static SoundEvent block(String block, String type) { return register("block." + block + "." + type); }
    private static SoundEvent entity(String entity, String type) { return register("entity." + entity + "." + type); }
    private static SoundEvent item(String item, String type) { return register("item." + item + "." + type); }

    private static SoundEvent register(String name) {
        Identifier identifier = new Identifier(MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }
}
