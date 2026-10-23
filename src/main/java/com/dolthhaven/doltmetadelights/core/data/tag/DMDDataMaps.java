package com.dolthhaven.doltmetadelights.core.data.tag;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@EventBusSubscriber(modid = DoltsMetadelights.MOD_ID)
public class DMDDataMaps {
    public static final DataMapType<Block, ResourceLocation> CAKE_SLICE_MAP = DataMapType
            .builder(DoltsMetadelights.rl("cake_slice_map"), Registries.BLOCK, ResourceLocation.CODEC)
            .build();

    @SubscribeEvent
    public static void registerDataMaps(RegisterDataMapTypesEvent event) {
        event.register(CAKE_SLICE_MAP);
    }
}
