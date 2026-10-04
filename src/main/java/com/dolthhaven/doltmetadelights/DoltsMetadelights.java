package com.dolthhaven.doltmetadelights;

import com.dolthhaven.doltmetadelights.core.data.DMDBlockStateModel;
import com.dolthhaven.doltmetadelights.core.data.DMDLootTables;
import com.dolthhaven.doltmetadelights.core.data.DMDRecipes;
import com.dolthhaven.doltmetadelights.core.data.tag.DMDBlockTags;
import com.dolthhaven.doltmetadelights.core.data.tag.DMDDataMaps;
import com.dolthhaven.doltmetadelights.core.data.tag.DMDItemTags;
import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.dolthhaven.doltmetadelights.core.registry.DMDBlocks;
import com.dolthhaven.doltmetadelights.core.registry.DMDEntities;
import com.dolthhaven.doltmetadelights.core.registry.DMDItems;
import com.mojang.logging.LogUtils;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

@Mod(DoltsMetadelights.MOD_ID)
public class DoltsMetadelights {
    public static final String MOD_ID = "dolts_metadelights";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

    public DoltsMetadelights(IEventBus bus, ModContainer modContainer) {
        DMDBlocks.BLOCKS.register(bus);
        DMDItems.ITEMS.register(bus);
        DMDEntities.ENTITY_TYPES.register(bus);

        bus.addListener(this::clientSetup);
        bus.addListener(this::dataSetup);

        modContainer.registerConfig(ModConfig.Type.COMMON, DMDConfig.COMMON_SPEC);
    }

    private void dataSetup(GatherDataEvent event) {
        boolean includeServer = event.includeServer();
        var dataGen = event.getGenerator();

        DMDBlockTags taggies = new DMDBlockTags(event);
        dataGen.addProvider(includeServer, taggies);
        dataGen.addProvider(includeServer, new DMDItemTags(event, taggies));
        dataGen.addProvider(includeServer, new DMDDataMaps(event));
        dataGen.addProvider(includeServer, new DMDLootTables(event));
        dataGen.addProvider(includeServer, new DMDRecipes(event));

        boolean includeClient = event.includeClient();
        dataGen.addProvider(includeClient, new DMDBlockStateModel(event));
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(DMDItems::setUpTabEditors);
    }
}
