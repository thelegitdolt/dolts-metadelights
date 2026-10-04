package com.dolthhaven.doltmetadelights.core.registry;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.common.block.GlowshroomColonyBlock;
import com.dolthhaven.doltmetadelights.utils.Consts;
import com.dolthhaven.doltmetadelights.utils.RegUtil;
import com.teamabnormals.blueprint.common.block.BlueprintDirectionalBlock;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import static net.minecraft.world.level.material.MapColor.GLOW_LICHEN;

public class DMDBlocks {
    public static final BlockSubRegistryHelper BLOCKS = DoltsMetadelights.REGISTRY_HELPER.getBlockSubHelper();

    public static final DeferredBlock<Block> MULCH_BAG = BLOCKS.createBlock("mulch_bag", () ->
            new BlueprintDirectionalBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.RICE_BAG.get()).mapColor(MapColor.COLOR_BROWN)));


    public static final DeferredBlock<Block> GLOW_SHROOM_COLONY = BLOCKS.createBlockNoItem("glow_shroom_colony", () ->
            new GlowshroomColonyBlock(DMDProps.GLOW_SHROOM_COLONY));

    public static final DeferredBlock<Block> BOP_GLOWSHROOM_COLONY = BLOCKS.createBlockNoItem("bop_glowshroom_colony", () ->
            new MushroomColonyBlock(RegUtil.itemHolderOr(Consts.GLOWSHROOM_BOP, Items.RED_MUSHROOM), DMDProps.BOP_GLOWSHROOM_COLONY));
    public static final DeferredBlock<Block> TOADSTOOL_COLONY = BLOCKS.createBlockNoItem("toadstool_colony", () ->
            new MushroomColonyBlock(RegUtil.itemHolderOr(Consts.GLOWSHROOM_BOP, Items.BROWN_MUSHROOM), BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM)));

    public static class DMDProps {
        public static final BlockBehaviour.Properties BOP_GLOWSHROOM_COLONY = BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM).mapColor(GLOW_LICHEN).lightLevel(state -> 6);
        public static final BlockBehaviour.Properties GLOW_SHROOM_COLONY = BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM)
                .randomTicks().lightLevel(s -> 10);
    }
}
