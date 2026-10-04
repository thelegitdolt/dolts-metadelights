package com.dolthhaven.doltmetadelights.core.mixin.mnd;

import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.dolthhaven.doltmetadelights.utils.ThisIsAGreatIdea;
import com.soytutta.mynethersdelight.common.block.feasts.GhastaWithCreamBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GhastaWithCreamBlock.class)
public class GhastaWithCreamBlockMixin {
    @Inject(method = "isRandomlyTicking", at = @At("HEAD"), cancellable = true)
    private void sex(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (!ThisIsAGreatIdea.tryGet(DMDConfig.COMMON.ghastaWithCreamDoesntRegenerate)) return;
        cir.setReturnValue(false);
    }
}
