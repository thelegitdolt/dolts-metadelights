package com.dolthhaven.doltmetadelights.integration;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import umpaz.brewinandchewin.common.registry.BnCEffects;
import umpaz.brewinandchewin.common.registry.BnCItems;

public class DMDBnCIntegration {
    public static boolean canShootTankard(Player player, ItemStack stack) {
        return stack.is(BnCItems.TANKARD) && player.getEffect(BnCEffects.TIPSY) != null;
    }

    public static Item tankard() {
        return BnCItems.TANKARD;
    }

}
