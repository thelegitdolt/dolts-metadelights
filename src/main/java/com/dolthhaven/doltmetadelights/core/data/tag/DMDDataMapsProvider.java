package com.dolthhaven.doltmetadelights.core.data.tag;

import com.dolthhaven.doltmetadelights.utils.Consts;
import com.dolthhaven.doltmetadelights.utils.RegUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import static com.dolthhaven.doltmetadelights.core.registry.DMDBlocks.*;

public class DMDDataMapsProvider extends DataMapProvider {
    public DMDDataMapsProvider(GatherDataEvent event) {
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
                .add(MULCH_BAG.getId(), new FurnaceFuel(1600), false);

        this.builder(DMDDataMaps.CAKE_SLICE_MAP)
                .add(Consts.NEAPOLITAN.rl("vanilla_cake"), Consts.ABNORMALS_DELIGHT.rl("vanilla_cake_slice"), false)
                .add(Consts.NEAPOLITAN.rl("chocolate_cake"), Consts.ABNORMALS_DELIGHT.rl("chocolate_cake_slice"), false)
                .add(Consts.NEAPOLITAN.rl("strawberry_cake"), Consts.ABNORMALS_DELIGHT.rl("strawberry_cake_slice"), false)
                .add(Consts.NEAPOLITAN.rl("banana_cake"), Consts.ABNORMALS_DELIGHT.rl("banana_cake_slice"), false)
                .add(Consts.NEAPOLITAN.rl("mint_cake"), Consts.ABNORMALS_DELIGHT.rl("mint_cake_slice"), false)
                .add(Consts.NEAPOLITAN.rl("adzuki_cake"), Consts.ABNORMALS_DELIGHT.rl("adzuki_cake_slice"), false)
                .add(Consts.MY_NETHERS_DELIGHT.rl("magma_cake_block"), Consts.MY_NETHERS_DELIGHT.rl("magma_cake_slice"), false)
                .add(Consts.DUNGEONS_DELIGHT.rl("monster_cake"), Consts.DUNGEONS_DELIGHT.rl("monster_cake_slice"), false);
    }
}
