package com.ninni.frozenup.entity;

import java.util.Arrays;
import java.util.Comparator;

public enum PenguinMood {
    HAPPY(0, "happy"),
    CONFUSED(1, "confused"),
    FOCUSED(2, "focused"),
    AGITATED(3, "agitated");

    public static final PenguinMood[] MOODS = Arrays.stream(values()).sorted(Comparator.comparingInt(PenguinMood::getId)).toArray(PenguinMood[]::new);
    private final int id;
    private final String name;

    PenguinMood(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
