package com.dolthhaven.doltmetadelights;

import com.dolthhaven.doltmetadelights.core.data.DMDLootTables;
import com.dolthhaven.doltmetadelights.core.data.DMDRecipes;
import com.dolthhaven.doltmetadelights.core.data.client.DMDBlockStateModel;
import com.dolthhaven.doltmetadelights.core.data.client.DMDItemModelGen;
import com.dolthhaven.doltmetadelights.core.data.dataregistries.DMDEnchantments;
import com.dolthhaven.doltmetadelights.core.data.tag.DMDBlockTags;
import com.dolthhaven.doltmetadelights.core.data.tag.DMDDataMaps;
import com.dolthhaven.doltmetadelights.core.data.tag.DMDEnchantTags;
import com.dolthhaven.doltmetadelights.core.data.tag.DMDItemTags;
import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.dolthhaven.doltmetadelights.core.registry.*;
import com.mojang.logging.LogUtils;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod(DoltsMetadelights.MOD_ID)
public class DoltsMetadelights {
    public static final String MOD_ID = "dolts_metadelights";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

    public DoltsMetadelights(IEventBus bus, ModContainer modContainer) {
        registerToAllRegistries(bus);

        bus.addListener(this::clientSetup);
        bus.addListener(this::dataSetup);

        modContainer.registerConfig(ModConfig.Type.COMMON, DMDConfig.COMMON_SPEC);
    }

    private static void registerToAllRegistries(IEventBus bus) {
        DMDBlocks.BLOCKS.register(bus);
        DMDItems.ITEMS.register(bus);
        DMDEntities.ENTITY_TYPES.register(bus);
        DMDSounds.SOUND_EVENTS.register(bus);

        DMDFluids.FLUID_TYPES.register(bus);
        DMDFluids.FLUIDS.register(bus);

        DMDEnchantEffectComponents.ENCHANTMENT_COMPONENT_TYPES.register(bus);
        DMDLootConditions.CONDITIONS.register(bus);
    }

    private void dataSetup(GatherDataEvent event) {
        var dataGen = event.getGenerator();

        RegistrySetBuilder registrySetBuilder = new RegistrySetBuilder()
                .add(Registries.ENCHANTMENT, DMDEnchantments::bootstrap);
        DatapackBuiltinEntriesProvider datapackProvider = new DatapackBuiltinEntriesProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), registrySetBuilder, Set.of(MOD_ID));
        CompletableFuture<HolderLookup.Provider> newProvider = datapackProvider.getRegistryProvider();

        boolean includeServer = event.includeServer();

        dataGen.addProvider(includeServer, datapackProvider);
        DMDBlockTags taggies = new DMDBlockTags(event);
        dataGen.addProvider(includeServer, taggies);
        dataGen.addProvider(includeServer, new DMDItemTags(event, taggies));
        dataGen.addProvider(includeServer, new DMDDataMaps(event));
        dataGen.addProvider(includeServer, new DMDLootTables(event));
        dataGen.addProvider(includeServer, new DMDRecipes(event));
        dataGen.addProvider(includeServer, new DMDEnchantTags(event, newProvider));

        boolean includeClient = event.includeClient();
        dataGen.addProvider(includeClient, new DMDBlockStateModel(event));
        dataGen.addProvider(includeClient, new DMDItemModelGen(event));
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(DMDItems::setUpTabEditors);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
