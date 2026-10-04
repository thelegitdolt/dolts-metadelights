package com.dolthhaven.doltmetadelights.core.registry;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.teamabnormals.blueprint.core.util.registry.SoundSubRegistryHelper;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DMDSounds {
    public static final SoundSubRegistryHelper SOUND_EVENTS = DoltsMetadelights.REGISTRY_HELPER.getSoundSubHelper();

    public static final DeferredHolder<SoundEvent, SoundEvent> TANKARD_SHOOTS = SOUND_EVENTS.createSoundEvent(id("item.%s.tankard_shoots"));
    public static final DeferredHolder<SoundEvent, SoundEvent> TANKARD_HIT = SOUND_EVENTS.createSoundEvent(id("item.%s.tankard_hit"));

    private static String id(String str) {
        return str.formatted(DoltsMetadelights.MOD_ID);
    }
}
