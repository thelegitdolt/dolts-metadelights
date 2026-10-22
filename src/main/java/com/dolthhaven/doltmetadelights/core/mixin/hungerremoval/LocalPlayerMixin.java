package com.dolthhaven.doltmetadelights.core.mixin.hungerremoval;

import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// hunger effect prevents sprinting
@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Inject(method = "canStartSprinting", at = @At(value = "HEAD"), cancellable = true)
    private void DoltModHow$HungerStopsSprinting(CallbackInfoReturnable<Boolean> cir) {
        if (!DMDConfig.COMMON.hungerEffectPreventsSprint.get()) return;
        if (((LocalPlayer) (Object)this).hasEffect(MobEffects.HUNGER)) cir.setReturnValue(false);
    }
}
