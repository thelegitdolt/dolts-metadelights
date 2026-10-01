package com.dolthhaven.doltmetadelights.core.data.tag;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.ItemTagsProvider;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DMDItemTags extends ItemTagsProvider {
    public DMDItemTags(GatherDataEvent event, BlockTagsProvider provider) {
        super(event.getGenerator().getPackOutput(), event.getLookupProvider(), provider.contentsGetter(), DoltsMetadelights.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

    }
}
