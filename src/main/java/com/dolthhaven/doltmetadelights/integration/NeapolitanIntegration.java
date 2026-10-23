package com.dolthhaven.doltmetadelights.integration;

import com.teamabnormals.neapolitan.core.NeapolitanConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class NeapolitanIntegration {
    public static boolean isWhiteStrawberry(Level level, BlockPos pos) {
        return pos.getY() >= NeapolitanConfig.COMMON.whiteStrawberryMinHeight.get() && level.dimension() == Level.OVERWORLD || level.dimension() == Level.END;
    }
}
