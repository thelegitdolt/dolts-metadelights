package com.dolthhaven.doltmetadelights.core.event;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.dolthhaven.doltmetadelights.utils.Consts;
import com.dolthhaven.doltmetadelights.utils.RegUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = DoltsMetadelights.MOD_ID)
public class DMDEvents {
    @SubscribeEvent
    private static void handleBulletPepper(PlayerInteractEvent.RightClickBlock event) {
        if (!DMDConfig.COMMON.killBulletPepperPlacement.get() || !Consts.MY_NETHERS_DELIGHT.loaded())
            return;

        ItemStack stack = event.getItemStack();
        Item bulletPepper = RegUtil.item(Consts.BULLET_PEPPER);
        if (bulletPepper != null && stack.is(bulletPepper)) {
            event.setUseItem(TriState.FALSE);
        }
    }
}
