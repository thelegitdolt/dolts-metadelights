package com.dolthhaven.doltmetadelights.integration;

import com.dolthhaven.doltmetadelights.core.registry.DMDItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import umpaz.brewinandchewin.common.block.CheeseWheelBlock;
import umpaz.brewinandchewin.common.registry.BnCEffects;
import umpaz.brewinandchewin.common.registry.BnCItems;

import java.util.function.Supplier;

public class DMDBnCIntegration {
    public static boolean canShootTankard(Player player, ItemStack stack) {
        return stack.is(BnCItems.TANKARD) && player.getEffect(BnCEffects.TIPSY) != null;
    }

    public static Item tankard() {
        return BnCItems.TANKARD;
    }

    public static final Supplier<Block> WARDENZOLA = () ->
            new CheeseWheelBlock(DMDItems.WARDENZOLA_WEDGE, BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE));
}
