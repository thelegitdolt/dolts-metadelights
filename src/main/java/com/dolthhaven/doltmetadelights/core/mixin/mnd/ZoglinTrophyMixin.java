package com.dolthhaven.doltmetadelights.core.mixin.mnd;

import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.soytutta.mynethersdelight.common.block.trophies.TrophyBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(TrophyBlock.class)
public class ZoglinTrophyMixin extends Block  {
    public ZoglinTrophyMixin(Properties p_49795_) {
        super(p_49795_);
    }

    @Inject(method = "isRandomlyTicking", at = @At("HEAD"), cancellable = true)
    private void sex(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (!DMDConfig.COMMON.hoglinMountDoesntTick.get()) return;
        cir.setReturnValue(false);
    }

    @Inject(method = "animateTick", at = @At("HEAD"),  cancellable = true)
    private void amogus(BlockState state, Level level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (!DMDConfig.COMMON.hoglinMountDoesntTick.get()) return;
        super.animateTick(state, level, pos, random);
        ci.cancel();
    }
}
