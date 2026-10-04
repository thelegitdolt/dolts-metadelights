package com.dolthhaven.doltmetadelights.core.data.client;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.teamabnormals.blueprint.core.data.client.BlueprintItemModelProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import static com.dolthhaven.doltmetadelights.core.registry.DMDItems.WARDENZOLA_WEDGE;

public class DMDItemModelGen extends BlueprintItemModelProvider {
    public DMDItemModelGen(GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), DoltsMetadelights.MOD_ID, e.getExistingFileHelper());
    }

    @Override
    protected void registerModels() {
        generatedItem(WARDENZOLA_WEDGE);
    }
}
