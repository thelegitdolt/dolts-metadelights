package com.dolthhaven.doltmetadelights.core.mixin.bnc;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import umpaz.brewinandchewin.common.block.CoasterBlock;
import umpaz.brewinandchewin.common.block.entity.CoasterBlockEntity;
import umpaz.brewinandchewin.common.registry.BnCBlocks;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;

@Pseudo
@Mixin(CoasterBlockEntity.class)
public class CoasterEntityMixin extends SyncedBlockEntity {
    public CoasterEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Definition(id = "AIR", field = "Lnet/minecraft/world/level/block/Blocks;AIR:Lnet/minecraft/world/level/block/Block;")
    @Definition(id = "defaultBlockState", method = "Lnet/minecraft/world/level/block/Block;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;")
    @Expression("AIR.defaultBlockState()")
    @ModifyExpressionValue(method = "useWithoutItem", at = @At("MIXINEXTRAS:EXPRESSION"), remap = false)
    private BlockState thing(BlockState original, @Local(argsOnly = true) Level level, @Local(argsOnly = true) BlockPos pos, @Share("isReal") LocalBooleanRef isReal) {
        isReal.set(true);
        level.scheduleTick(pos, BnCBlocks.COASTER, 100);
        return BnCBlocks.COASTER.defaultBlockState().setValue(CoasterBlock.INVISIBLE, true);
    }

    @WrapOperation(method = "useWithoutItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean real(Level instance, BlockPos pos, BlockState state, Operation<Boolean> original, @Share("isReal") LocalBooleanRef isReal) {
        return original.call(instance, pos, state);
    }
}
