package com.dolthhaven.doltmetadelights.core;

import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.dolthhaven.doltmetadelights.utils.Consts;
import com.dolthhaven.doltmetadelights.utils.RegUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class DMDEvents {
    private static void handleBulletPepper(PlayerInteractEvent.RightClickBlock event) {
        if (!DMDConfig.COMMON.killBulletPepperPlacement.get() || !ModList.get().isLoaded(Consts.MY_NETHERS_DELIGHT))
            return;

        ItemStack stack = event.getItemStack();
        Item bulletPepper = RegUtil.item(Consts.BULLET_PEPPER);
        if (bulletPepper != null && stack.is(bulletPepper)) {
            event.setUseItem(TriState.FALSE);
        }
    }
}
