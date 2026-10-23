package com.dolthhaven.doltmetadelights.core.mixin.atmospheric;

import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "com.teamabnormals.atmospheric.common.block.OrangeBlock")
public class OrangeMixin {
    @Inject(method = "fallOn", at = @At("HEAD"), cancellable = true)
    private void normalOranges(Level level, BlockState state, BlockPos pos, Entity entity, float distance, CallbackInfo ci) {
        if (DMDConfig.COMMON.removeOrangeVapor.get()) ci.cancel();
    }
}
