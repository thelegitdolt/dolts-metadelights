package com.dolthhaven.doltmetadelights.core.mixin.hungerremoval;

import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets="com.teamabnormals.atmospheric.common.effect.PersistenceEffect")
public class PersistenceMixin {
    @WrapOperation(method = "applyEffectTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;getFoodLevel()I"))
    private int sex(FoodData instance, Operation<Integer> original, @Local(argsOnly = true) LivingEntity player) {
        return DMDConfig.COMMON.persistenceEffectChecksHealth.get() ? (int) player.getHealth() : original.call(instance);
    }
}