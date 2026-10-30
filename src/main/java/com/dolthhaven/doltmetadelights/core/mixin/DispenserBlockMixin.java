package com.dolthhaven.doltmetadelights.core.mixin;

import com.dolthhaven.doltmetadelights.core.other.FakePlayerCauldronInteract;
import com.dolthhaven.doltmetadelights.utils.Consts;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlockMixin {
    @WrapOperation(
            method = "dispenseFrom",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/DispenserBlock;getDispenseMethod(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/core/dispenser/DispenseItemBehavior;"
            )
    )
    public DispenseItemBehavior onCuttingBoardDispenseFromInject(DispenserBlock instance, Level level, ItemStack stack, Operation<DispenseItemBehavior> original, @Local(argsOnly = true) BlockPos pos, @Local BlockSource source) {
        if (Consts.SUPPLEMENTARIES.loaded()) {
            BlockState state = level.getBlockState(pos.relative(source.state().getValue(DispenserBlock.FACING)));

            if (state.getBlock() instanceof AbstractCauldronBlock cauldron) {
                CauldronInteraction interaction = cauldron.interactions.map().get(stack.getItem());
                if (interaction != null) {
                    return new FakePlayerCauldronInteract(interaction, stack.getItem());
                }
            }
        }
        return original.call(instance, level, stack);
    }
}
