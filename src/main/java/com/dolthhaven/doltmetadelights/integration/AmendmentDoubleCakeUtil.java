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

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class AmendmentDoubleCakeUtil {
    // outputs the cake block and also slice item, null otherwise
    public static Pair<BlockState, Item> getCakeAndSlice(BlockState state) {
        if (state.getBlock() instanceof DoubleCakeBlock doubleCake) {
            ResourceLocation idrl = RegUtil.blockId(doubleCake);

            String id = idrl.getPath().replace("/double_", ":");
            var block = Optional.ofNullable(RegUtil.blockHolder(ResourceLocation.parse(id)));
            var item = block.map(blockHolder -> blockHolder.getData(DMDDataMaps.CAKE_SLICE_MAP))
                    .map(RegUtil::item);
            return item.isPresent() ? Pair.of(block.get().value().defaultBlockState(), item.get()) : null;
        }
        return null;
    }

    public static boolean isDoubleCakeBlock(BlockState block) {
        return block.getBlock() instanceof DoubleCakeBlock;
    }
}
