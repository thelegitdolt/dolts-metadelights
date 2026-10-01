package com.dolthhaven.doltmetadelights.core.data;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import vectorwing.farmersdelight.common.registry.ModItems;

import static com.dolthhaven.doltmetadelights.core.registry.DMDBlocks.MULCH_BAG;

public class DMDRecipes extends BlueprintRecipeProvider {
    public DMDRecipes(GatherDataEvent event) {
        super(DoltsMetadelights.MOD_ID, event.getGenerator().getPackOutput(), event.getLookupProvider());
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput, HolderLookup.Provider holderLookup) {
        storageRecipes(recipeOutput, RecipeCategory.MISC, ModItems.TREE_BARK.get(), RecipeCategory.BUILDING_BLOCKS, MULCH_BAG.asItem());
    }
}
