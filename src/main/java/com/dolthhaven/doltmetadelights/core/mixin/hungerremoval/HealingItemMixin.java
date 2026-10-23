package com.dolthhaven.doltmetadelights.core.mixin.hungerremoval;

import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.teamabnormals.neapolitan.common.item.HealingItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Pseudo
@Mixin(HealingItem.class)
public class HealingItemMixin {
    @ModifyArg(method = "finishUsingItem", at = @At(value = "INVOKE", target = "Lcom/teamabnormals/neapolitan/common/item/HealingItem;applyHealing(FLnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/LivingEntity;)V"))
    private float DoltModHow$HealEqualToNutrition(float healAmount) {
        Item item = (Item) (Object) this;
        FoodProperties food = item.getDefaultInstance().get(DataComponents.FOOD);
        if (DMDConfig.COMMON.strawberriesHealingTweaks.get() && food != null) {
            return food.nutrition();
        }
        return healAmount;
    }
}
