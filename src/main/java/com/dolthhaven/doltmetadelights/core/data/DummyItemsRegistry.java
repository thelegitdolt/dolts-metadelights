package com.dolthhaven.doltmetadelights.core.data;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.utils.Consts;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Set;

@EventBusSubscriber(modid = DoltsMetadelights.MOD_ID)
public class DummyItemsRegistry {
    public static final Set<ResourceLocation> DUMMY_IDS = Set.of(
            Consts.GLOW_SHROOM.loc(), Consts.GLOWSHROOM_BOP.loc(), Consts.TOADSTOOL_BOP.loc()
    );

    @SubscribeEvent
    public static void registerDummies(RegisterEvent event) {
        if (DatagenModLoader.isRunningDataGen()) {
            DUMMY_IDS.forEach(id -> {
                event.register(Registries.ITEM, id, () -> new Item(new Item.Properties()));
            });
        }
    }
}
