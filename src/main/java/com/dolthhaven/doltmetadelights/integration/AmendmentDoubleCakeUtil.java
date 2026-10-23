package com.dolthhaven.doltmetadelights.integration;

import com.dolthhaven.doltmetadelights.core.data.tag.DMDDataMaps;
import com.dolthhaven.doltmetadelights.utils.RegUtil;
import com.mojang.datafixers.util.Pair;
import net.mehvahdjukaar.amendments.common.block.DoubleCakeBlock;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class AmendmentDoubleCakeUtil {
    public static Pair<BlockState, Item> getCakeAndSlice(BlockState state) {
        if (state.getBlock() instanceof DoubleCakeBlock doubleCake) {
            ResourceLocation idrl = RegUtil.blockId(doubleCake);

            String id = idrl.getPath().replace("/double_", ":");
            Holder<Block> block = RegUtil.blockHolder(ResourceLocation.parse(id));
            if (block == null) return null;
            ResourceLocation sliceLoc = block.getData(DMDDataMaps.CAKE_SLICE_MAP);
            if (sliceLoc == null) return null;
            Item sliceItem = RegUtil.item(sliceLoc);
            if (sliceItem == null) return null;
            return Pair.of(block.value().defaultBlockState(), sliceItem);
        }
        return null;
    }

    public static boolean isDoubleCakeBlock(BlockState block) {
        return block.getBlock() instanceof DoubleCakeBlock;
    }
}
