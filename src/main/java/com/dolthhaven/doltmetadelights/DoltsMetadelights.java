package com.dolthhaven.doltmetadelights;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(DoltsMetadelights.MODID)
public class DoltsMetadelights {
    public static final String MODID = "dolts_metadelights";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DoltsMetadelights(IEventBus modEventBus, ModContainer modContainer) {
    }
}
