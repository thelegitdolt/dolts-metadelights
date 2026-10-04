package com.dolthhaven.doltmetadelights.core.registry;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.utils.Consts;
import com.dolthhaven.doltmetadelights.utils.RegUtil;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemFrameItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import vectorwing.farmersdelight.common.item.MushroomColonyItem;
import vectorwing.farmersdelight.common.registry.ModCreativeTabs;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.Predicate;

import static net.minecraft.world.item.crafting.Ingredient.of;
import static com.dolthhaven.doltmetadelights.core.registry.DMDBlocks.MULCH_BAG;

public class DMDItems {
    public static final ItemSubRegistryHelper ITEMS = DoltsMetadelights.REGISTRY_HELPER.getItemSubHelper();

    public static final DeferredItem<Item> GLOW_SHROOM_COLONY = ITEMS.createItem("glow_shroom_colony", () -> new MushroomColonyItem(DMDBlocks.GLOW_SHROOM_COLONY.get(), new Item.Properties()));
    public static final DeferredItem<Item> BOP_GLOWSHROOM_COLONY = ITEMS.createItem("bop_glowshroom_colony", () -> new MushroomColonyItem(DMDBlocks.BOP_GLOWSHROOM_COLONY.get(), new Item.Properties()));
    public static final DeferredItem<Item> TOADSTOOL_COLONY = ITEMS.createItem("toadstool_colony", () -> new MushroomColonyItem(DMDBlocks.TOADSTOOL_COLONY.get(), new Item.Properties()));


    public static void setUpTabEditors() {
        var thing = CreativeModeTabContentsPopulator.mod(DoltsMetadelights.MOD_ID)
                .tab(CreativeModeTabs.NATURAL_BLOCKS)
                .addItemsAfter(of(Blocks.HAY_BLOCK), MULCH_BAG)
                .predicate(DMDItems::fdPredicate)
                .addItemsAfter(of(ModItems.RED_MUSHROOM_COLONY.get()), GLOW_SHROOM_COLONY)
                .addItemsAfter(ofModLoaded(ModItems.RED_MUSHROOM_COLONY.get(), Consts.BOP), TOADSTOOL_COLONY, BOP_GLOWSHROOM_COLONY);
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
}
