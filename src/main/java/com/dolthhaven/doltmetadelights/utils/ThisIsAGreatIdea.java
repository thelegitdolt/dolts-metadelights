package com.dolthhaven.doltmetadelights.utils;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ThisIsAGreatIdea {
    public static boolean tryGet(ModConfigSpec.ConfigValue<Boolean> config) {
        try {
            return config.get();
        } catch (IllegalStateException ignored){}

        return config.getDefault();
    }
}
