package com.dolthhaven.doltmetadelights.core.data.tag;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.ItemTagsProvider;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.yirmiri.dungeonsdelight.core.init.DDTags;
import umpaz.brewinandchewin.common.tag.BnCTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import static com.dolthhaven.doltmetadelights.core.registry.DMDItems.WARDENZOLA_WEDGE;

public class DMDItemTags extends ItemTagsProvider {
    public DMDItemTags(GatherDataEvent event, BlockTagsProvider provider) {
        super(event.getGenerator().getPackOutput(), event.getLookupProvider(), provider.contentsGetter(), DoltsMetadelights.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.copy(ModTags.Blocks.MUSHROOM_COLONIES, ModTags.Items.MUSHROOM_COLONIES);

        this.tag(BnCTags.Items.FOOD_CHEESE_WEDGE).add(WARDENZOLA_WEDGE.get());
        this.tag(DDTags.ItemT.SCULK_CHEESE).add(WARDENZOLA_WEDGE.get());
    }
}
