package com.ninni.frozenup.network;

import net.minecraft.util.Identifier;

import static com.ninni.frozenup.FrozenUp.*;

public interface FrozenUpPacketIdentifiers {
    Identifier OPEN_REINDEER_SCREEN = create("open_reindeer_screen");

    static Identifier create(String id) {
        return new Identifier(MOD_ID, id);
    }
}
