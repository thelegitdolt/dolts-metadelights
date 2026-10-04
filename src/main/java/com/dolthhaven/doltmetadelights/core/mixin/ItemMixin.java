package com.dolthhaven.doltmetadelights.core.mixin;

import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.dolthhaven.doltmetadelights.core.registry.DMDBlocks;
import com.dolthhaven.doltmetadelights.core.registry.DMDItems;
import com.dolthhaven.doltmetadelights.utils.Consts;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "useOn", at = @At(value = "RETURN"), cancellable = true)
    private void DoltModHow$PlaceItems(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        InteractionResult result = cir.getReturnValue();

        if (result == InteractionResult.PASS) {
            Item self = (Item) (Object) this;
            if (Consts.DUNGEONS_DELIGHT.loaded() && DMDConfig.COMMON.wheelifiedWardenzola.get() && self == Consts.DD_WARDENZOLA.lookup()) {
                BlockPlaceContext context = new BlockPlaceContext(useOnContext);
                InteractionResult newResult = ((BlockItem) DMDBlocks.WARDENZOLA.asItem()).place(context);
                if (newResult.consumesAction()) {
                    cir.setReturnValue(newResult);
                }
            }
        }
    }
}
