package com.dolthhaven.doltmetadelights.core.data;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.teamabnormals.blueprint.core.data.client.BlueprintBlockStateProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import static com.dolthhaven.doltmetadelights.core.registry.DMDBlocks.MULCH_BAG;

public class DMDBlockStateModel extends BlueprintBlockStateProvider {
    public DMDBlockStateModel(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), DoltsMetadelights.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void registerStatesAndModels() {
        this.directionalBlock(MULCH_BAG);
    }
}
