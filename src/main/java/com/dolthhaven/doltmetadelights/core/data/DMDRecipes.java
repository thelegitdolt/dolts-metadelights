package com.dolthhaven.doltmetadelights.core.data;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.dolthhaven.doltmetadelights.core.registry.DMDFluids;
import com.dolthhaven.doltmetadelights.core.registry.DMDLootConditions;
import com.dolthhaven.doltmetadelights.utils.Consts;
import com.teamabnormals.blueprint.core.api.conditions.ConfigValueCondition;
import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.AndCondition;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.crafting.CompoundIngredient;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.yirmiri.dungeonsdelight.core.registry.DDItems;
import umpaz.brewinandchewin.client.recipebook.FermentingBookCategory;
import umpaz.brewinandchewin.common.registry.BnCFluids;
import umpaz.brewinandchewin.common.utility.FluidUnit;
import umpaz.brewinandchewin.data.builder.KegFermentingRecipeBuilder;
import vectorwing.farmersdelight.common.crafting.ingredient.ItemAbilityIngredient;
import vectorwing.farmersdelight.common.item.KnifeItem;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.HashMap;
import java.util.List;

import static com.dolthhaven.doltmetadelights.core.registry.DMDBlocks.MULCH_BAG;
import static com.dolthhaven.doltmetadelights.core.registry.DMDItems.WARDENZOLA_WEDGE;
import static net.minecraft.world.item.crafting.Ingredient.of;

public class DMDRecipes extends BlueprintRecipeProvider {
    private static final Ingredient KNIVES = matchesTool(KnifeItem.KNIFE_DIG, CommonTags.Items.TOOLS_KNIFE);

    public DMDRecipes(GatherDataEvent event) {
        super(DoltsMetadelights.MOD_ID, event.getGenerator().getPackOutput(), event.getLookupProvider());
    }

    @Override
    protected void buildRecipes(RecipeOutput output, HolderLookup.Provider holderLookup) {
        storageRecipes(output, RecipeCategory.MISC, ModItems.TREE_BARK.get(), RecipeCategory.BUILDING_BLOCKS, MULCH_BAG.asItem());
        twoByTwoCustomLoc(output.withConditions(wardenzola()), RecipeCategory.FOOD, DDItems.WARDENZOLA.get(), WARDENZOLA_WEDGE.get(), "wardenzola_from_wedges");

        KegFermentingRecipeBuilder.kegFermentingRecipe(FermentingBookCategory.MEALS, DMDFluids.WARDENZOLA_SOURCE.get(), 1000, 9600, 1.0F, 4)
                .addFluidIngredient(Tags.Fluids.MILK, 1000, FluidUnit.MILLIBUCKET).setFluidUnit(FluidUnit.MILLIBUCKET)
                .addIngredient(Items.SCULK).addIngredient(Items.SCULK).addIngredient(DDItems.ROTBULB.get())
                .unlockedByItems("has_sculk", Items.SCULK)
                .build(output.withConditions(wardenzola()), DoltsMetadelights.rl("fermenting/wardenzola"));

        CuttingBoardRecipeBuilder.cuttingRecipe(of(DDItems.WARDENZOLA.get()), KNIVES, WARDENZOLA_WEDGE.get(), 4)
                .save(output.withConditions(wardenzola()), DoltsMetadelights.rl("wardenzola_wedge_cut"));
        CuttingBoardRecipeBuilder.cuttingRecipe(of(DDItems.WARDENZOLA.get()), KNIVES, DDItems.WARDENZOLA_CRUMBLES.get(), 2)
                .save(output.withConditions(not(wardenzola())), DoltsMetadelights.rl("wardenzola_crumble_cut"));
    }

    private static ICondition not(ICondition condition) {
        return new NotCondition(condition);
    }

    private static ICondition wardenzola() {
        return new AndCondition(List.of(new ConfigValueCondition(DMDLootConditions.CONFIG.get(), DMDConfig.COMMON.wheelifiedWardenzola,"placeable_wardenzola", new HashMap<>(), false), Consts.BnC.requiresLoaded(), Consts.DUNGEONS_DELIGHT.requiresLoaded()));
    }

    private static Ingredient matchesTool(ItemAbility toolAction, TagKey<Item> fallbackTag) {
        return CompoundIngredient.of((new ItemAbilityIngredient(toolAction)).toVanilla(), Ingredient.of(fallbackTag));
    }

    protected static void twoByTwoCustomLoc(RecipeOutput recipeOutput, RecipeCategory category, ItemLike packed, ItemLike unpacked, String savePath) {
        ShapedRecipeBuilder.shaped(category, packed, 1).define('#', unpacked).pattern("##").pattern("##").unlockedBy(getHasName(unpacked), has(unpacked)).save(recipeOutput, DoltsMetadelights.rl(savePath));
    }
}
