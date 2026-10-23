package com.dolthhaven.doltmetadelights.utils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DoltUtils {
    public static void addToInvOrDrop(Player player, ItemStack stack) {
        if (!player.getInventory().add(stack))
            player.drop(stack, true);
    }
}
