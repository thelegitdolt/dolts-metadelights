package com.dolthhaven.doltmetadelights.core.mixin;

import com.dolthhaven.doltmetadelights.core.data.dataregistries.DMDEnchantments;
import com.dolthhaven.doltmetadelights.utils.Consts;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.item.KnifeItem;

@Mixin(KnifeItem.class)
public class KnifeItemMixin {
    // knifes can't receive ballistic enchantment if dungeon's delight is not installed
    @Inject(method = "supportsEnchantment", at = @At("HEAD"), cancellable = true)
    public void supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment, CallbackInfoReturnable<Boolean> cir) {
        if (Consts.DUNGEONS_DELIGHT.loaded()) return;

        if (enchantment.unwrapKey().orElse(null) == DMDEnchantments.BALLISTIC) {
            cir.setReturnValue(false);
        }
    }
}
