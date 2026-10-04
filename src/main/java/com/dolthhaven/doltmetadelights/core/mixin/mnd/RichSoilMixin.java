package com.dolthhaven.doltmetadelights.core.mixin.mnd;

import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.dolthhaven.doltmetadelights.core.registry.DMDBlocks;
import com.dolthhaven.doltmetadelights.utils.Consts;
import com.dolthhaven.doltmetadelights.utils.RegUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.block.RichSoilBlock;

@Mixin(RichSoilBlock.class)
public class RichSoilMixin {
    @Inject(method = "randomTick",
            at = @At("HEAD"))
    private void DoltModHow$GrowCustomColonies(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand, CallbackInfo ci) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);
        ResourceLocation aboveLoc = RegUtil.blockId(aboveState.getBlock());

        if (level.isClientSide) return;

        if (DMDConfig.COMMON.doRichSoilGrowFungusColony.get()) {
            if (Consts.MY_NETHERS_DELIGHT.loaded()) {
                if (aboveState.is(Blocks.CRIMSON_FUNGUS)) {
                    Block block = RegUtil.block(Consts.MY_NETHERS_DELIGHT.rl("crimson_fungus_colony"));
                    if (block != null) {
                        level.setBlockAndUpdate(abovePos, block.defaultBlockState());
                        return;
                    }
                }
                else if (aboveState.is(Blocks.WARPED_FUNGUS)) {
                    Block block = RegUtil.block(Consts.MY_NETHERS_DELIGHT.rl("warped_fungus_colony"));
                    if (block != null) {
                        level.setBlockAndUpdate(abovePos, block.defaultBlockState());
                        return;
                    }
                }
            }
        }


        if (aboveLoc.equals(Consts.GLOW_SHROOM)) {
            level.setBlockAndUpdate(abovePos, DMDBlocks.GLOW_SHROOM_COLONY.get().defaultBlockState());
            return;
        }

        if (Consts.BOP.loaded()) {
            if (aboveLoc.equals(Consts.GLOWSHROOM_BOP)) {
                level.setBlockAndUpdate(abovePos, DMDBlocks.BOP_GLOWSHROOM_COLONY.get().defaultBlockState());
            }
            else if (aboveLoc.equals(Consts.TOADSTOOL_BOP)) {
                level.setBlockAndUpdate(abovePos, DMDBlocks.TOADSTOOL_COLONY.get().defaultBlockState());
            }
        }

    }
}
