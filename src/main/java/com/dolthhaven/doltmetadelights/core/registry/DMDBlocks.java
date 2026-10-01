package com.dolthhaven.doltmetadelights.core.registry;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.teamabnormals.blueprint.common.block.BlueprintDirectionalBlock;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public class DMDBlocks {
    public static final BlockSubRegistryHelper BLOCKS = DoltsMetadelights.REGISTRY_HELPER.getBlockSubHelper();
    public static final ItemSubRegistryHelper ITEMS = DoltsMetadelights.REGISTRY_HELPER.getItemSubHelper();

    public static final DeferredBlock<Block> MULCH_BAG = BLOCKS.createBlock("mulch_bag", () ->
            new BlueprintDirectionalBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.RICE_BAG.get()).mapColor(MapColor.COLOR_BROWN)));

}
