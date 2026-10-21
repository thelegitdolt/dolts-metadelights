package com.dolthhaven.doltmetadelights.utils;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ThisIsAGreatIdea {
    public static <T> T tryGet(ModConfigSpec.ConfigValue<T> config) {
        try {
            return config.get();
        } catch (IllegalStateException ignored){}

        return config.getDefault();
    }
}
