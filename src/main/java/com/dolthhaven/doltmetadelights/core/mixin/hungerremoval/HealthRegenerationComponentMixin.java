package com.dolthhaven.doltmetadelights.core.mixin.hungerremoval;

import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets="antigers.melancholic_hunger.components.HealthRegenerationComponent")
public class HealthRegenerationComponentMixin {
    @WrapOperation(method = "eat(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Z", at = @At(value = "INVOKE", target = "Lantigers/melancholic_hunger/config/MelancholicConfig;getFoodHealth(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)I"), remap = false)
    private int DoltModHow$DoUmRadiationStuff(ItemStack itemStack, FoodProperties foodProperties, Operation<Integer> original) {
        if (DMDConfig.COMMON.strawberriesHealingTweaks.get()) {
            try {
                Class<?> healClass = Class.forName("com.teamabnormals.neapolitan.common.item.HealingItem");
                if (healClass.isInstance(itemStack.getItem())) {
                    return 0;
                }
            } catch (ClassNotFoundException ignored) {}
        }
        return original.call(itemStack, foodProperties);
    }
}
