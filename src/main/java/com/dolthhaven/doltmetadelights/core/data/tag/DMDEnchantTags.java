package com.dolthhaven.doltmetadelights.core.data.tag;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.core.data.dataregistries.DMDEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class DMDEnchantTags extends EnchantmentTagsProvider {
    public DMDEnchantTags(GatherDataEvent event, CompletableFuture<HolderLookup.Provider> provider) {
        super(event.getGenerator().getPackOutput(), provider, DoltsMetadelights.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(EnchantmentTags.NON_TREASURE).add(DMDEnchantments.BALLISTIC);
    }
}
