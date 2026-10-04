package com.dolthhaven.doltmetadelights.core.data.tag;

import net.minecraft.core.HolderLookup;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import static com.dolthhaven.doltmetadelights.core.registry.DMDBlocks.*;

public class DMDDataMaps extends DataMapProvider {
    public DMDDataMaps(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), event.getLookupProvider());
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        this.builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(MULCH_BAG.getId(), new Compostable(1.0f), false)
                .add(GLOW_SHROOM_COLONY.getId(), new Compostable(1.0f), false)
                .add(BOP_GLOWSHROOM_COLONY.getId(), new Compostable(1.0f), false)
                .add(TOADSTOOL_COLONY.getId(), new Compostable(1.0f), false);

        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(MULCH_BAG.getId(), new FurnaceFuel(300), false);
    }
}
