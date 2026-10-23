package com.dolthhaven.doltmetadelights.core.mixin;

import com.teamabnormals.neapolitan.core.Neapolitan;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Neapolitan.class)
public class Debug {
    @Inject(method = "dataSetup", at = @At("HEAD"), cancellable = true)
    private void kys(GatherDataEvent event, CallbackInfo ci) {ci.cancel();}
}
