package com.dolthhaven.doltmetadelights.core.registry;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;

import static net.minecraft.world.item.crafting.Ingredient.of;
import static com.dolthhaven.doltmetadelights.core.registry.DMDBlocks.MULCH_BAG;

public class DMDItems {
    public static void setUpTabEditors() {
        var thing = CreativeModeTabContentsPopulator.mod(DoltsMetadelights.MOD_ID)
                .tab(CreativeModeTabs.NATURAL_BLOCKS)
                .addItemsAfter(of(Blocks.HAY_BLOCK), MULCH_BAG);
    }
}
