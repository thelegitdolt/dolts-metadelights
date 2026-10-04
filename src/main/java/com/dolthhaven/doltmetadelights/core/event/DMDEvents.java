package com.dolthhaven.doltmetadelights.core.event;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.common.block.WardenzolaFluid;
import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.dolthhaven.doltmetadelights.utils.Consts;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import vectorwing.farmersdelight.data.recipe.CuttingRecipes;

@EventBusSubscriber(modid = DoltsMetadelights.MOD_ID)
public class DMDEvents {
    @SubscribeEvent
    private static void handleBulletPepper(PlayerInteractEvent.RightClickBlock event) {
        if (!DMDConfig.COMMON.killBulletPepperPlacement.get() || !Consts.MY_NETHERS_DELIGHT.loaded())
            return;

        ItemStack stack = event.getItemStack();
        Item bulletPepper = Consts.BULLET_PEPPER.lookup();
        if (bulletPepper != null && stack.is(bulletPepper)) {
            event.setUseItem(TriState.FALSE);
        }
    }

    @SubscribeEvent
    private static void changeDefaultItemProperties(ModifyDefaultComponentsEvent event) {
        Consts.DD_WARDENZOLA.safeLookup().ifPresent(item -> event.modify(item, builder -> {
            if (Consts.BnC.loaded()) builder.remove(DataComponents.FOOD);
        }));
    }
}
