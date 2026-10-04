package com.dolthhaven.doltmetadelights.core.mixin.mnd;

import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.dolthhaven.doltmetadelights.utils.Consts;
import com.dolthhaven.doltmetadelights.utils.RegUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import java.util.List;

@Pseudo
@Mixin(ConsumableItem.class)
public class ConsumableItemMixin {
    @Inject(method = "appendHoverText", at = @At("HEAD"), cancellable = true)
    private void DoltModHow$TOOLTIPSDIEDIEIDSNJDJKNEFNEKFNKWJNFKENKWENK(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag isAdvanced, CallbackInfo ci) {
        Item item = RegUtil.item(Consts.MY_NETHERS_DELIGHT.rl("magma_cake_slice"));

        if (item == null) return;

        if (stack.is(item) && DMDConfig.COMMON.frogsAreNotStupid.get()) {
            ci.cancel();
        }
    }
}
