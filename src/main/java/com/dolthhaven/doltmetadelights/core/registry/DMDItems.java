package com.dolthhaven.doltmetadelights.core.registry;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.common.item.ExperienceFoodItem;
import com.dolthhaven.doltmetadelights.utils.Consts;
import com.dolthhaven.doltmetadelights.utils.RegUtil;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import vectorwing.farmersdelight.common.item.MushroomColonyItem;
import vectorwing.farmersdelight.common.registry.ModCreativeTabs;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.Predicate;

import static com.dolthhaven.doltmetadelights.core.registry.DMDBlocks.*;
import static net.minecraft.world.item.crafting.Ingredient.of;

public class DMDItems {
    public static final ItemSubRegistryHelper ITEMS = DoltsMetadelights.REGISTRY_HELPER.getItemSubHelper();

    public static final DeferredItem<Item> WARDENZOLA_WEDGE = ITEMS.createItem("wardenzola_wedge",
            () -> new ExperienceFoodItem(new Item.Properties().food(Food.WARDENZOLA)));

    public static final DeferredItem<Item> GLOW_SHROOM_COLONY = ITEMS.createItem("glow_shroom_colony", () -> new MushroomColonyItem(DMDBlocks.GLOW_SHROOM_COLONY.get(), new Item.Properties()));
    public static final DeferredItem<Item> BOP_GLOWSHROOM_COLONY = ITEMS.createItem("bop_glowshroom_colony", () -> new MushroomColonyItem(DMDBlocks.BOP_GLOWSHROOM_COLONY.get(), new Item.Properties()));
    public static final DeferredItem<Item> TOADSTOOL_COLONY = ITEMS.createItem("toadstool_colony", () -> new MushroomColonyItem(DMDBlocks.TOADSTOOL_COLONY.get(), new Item.Properties()));

    public static void setUpTabEditors() {
        var thing = CreativeModeTabContentsPopulator.mod(DoltsMetadelights.MOD_ID)
                .tab(CreativeModeTabs.NATURAL_BLOCKS)
                .addItemsAfter(of(Blocks.HAY_BLOCK), MULCH_BAG)
                .predicate(DMDItems::fdPredicate)
                .addItemsAfter(of(ModItems.RED_MUSHROOM_COLONY.get()), GLOW_SHROOM_COLONY)
                .addItemsAfter(ofModLoaded(ModItems.RED_MUSHROOM_COLONY.get(), Consts.BOP.id()), TOADSTOOL_COLONY, BOP_GLOWSHROOM_COLONY)

                .predicate(DMDItems::dungeonsDelightPredicate)
                .addItemsAfter(ofID(Consts.DD_WARDENZOLA), WARDENZOLA_WEDGE);
    }

    public static boolean fdPredicate(BuildCreativeModeTabContentsEvent event) {
        return event.getTab() == ModCreativeTabs.TAB_FARMERS_DELIGHT.get();
    }

    public static Predicate<ItemStack> ofID(ResourceLocation location, String... modids) {
        return stack -> (BlockSubRegistryHelper.areModsLoaded(modids) && of(RegUtil.item(location)).test(stack));
    }

    public static Predicate<ItemStack> ofModLoaded(Item item, String... modids) {
        return stack -> (BlockSubRegistryHelper.areModsLoaded(modids) && of(item).test(stack));
    }



    public static boolean dungeonsDelightPredicate(BuildCreativeModeTabContentsEvent event) {
        return event.getTabKey().location().equals(Consts.DUNGEONS_DELIGHT_TAB);
    }

    public static class Food {
        public static final FoodProperties WARDENZOLA = new FoodProperties.Builder()
                .nutrition(3).saturationModifier(0.6f).build();
    }
}
