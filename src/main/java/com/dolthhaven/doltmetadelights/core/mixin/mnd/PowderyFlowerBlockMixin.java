package com.dolthhaven.doltmetadelights.core.mixin.mnd;

import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.dolthhaven.doltmetadelights.utils.Consts;
import com.dolthhaven.doltmetadelights.utils.RegUtil;
import com.soytutta.mynethersdelight.common.block.crops.PowderyFlowerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(PowderyFlowerBlock.class)
public abstract class PowderyFlowerBlockMixin extends BushBlock {
    public PowderyFlowerBlockMixin(Properties p_48957_) {
        super(p_48957_);
    }

    @Inject(method = "getCloneItemStack", at = @At("HEAD"), cancellable = true, remap = false)
    private void DoltModHow$CopyPowderyCaneLol(LevelReader level, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> cir) {
        Item item = RegUtil.item(Consts.MY_NETHERS_DELIGHT, "powder_cannon");

        if (item == null) return;
        if (DMDConfig.COMMON.killBulletPepperPlacement.get()) cir.setReturnValue(new ItemStack(item));
    }
//
//    @Override
//    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state, boolean bool) {
//        if (DMDConfig.COMMON.killBulletPepperPlacement.get()) return false;
//        else return super.isValidBonemealTarget(level, pos, state, bool);
//    }
}
