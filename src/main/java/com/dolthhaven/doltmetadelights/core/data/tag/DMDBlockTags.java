package com.dolthhaven.doltmetadelights.core.data.tag;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import net.minecraft.core.HolderLookup;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import umpaz.brewinandchewin.common.tag.BnCTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import static com.dolthhaven.doltmetadelights.core.registry.DMDBlocks.*;

public class DMDBlockTags extends BlockTagsProvider {
    public DMDBlockTags(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), event.getLookupProvider(), DoltsMetadelights.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModTags.Blocks.MINEABLE_WITH_KNIFE).add(MULCH_BAG.get(), WARDENZOLA.get());

        this.tag(BnCTags.Blocks.CHEESE_WHEELS_RIPE).add(WARDENZOLA.get());
        this.tag(ModTags.Blocks.MUSHROOM_COLONIES)
                .add(GLOW_SHROOM_COLONY.get(), BOP_GLOWSHROOM_COLONY.get(), TOADSTOOL_COLONY.get());
    }
}
