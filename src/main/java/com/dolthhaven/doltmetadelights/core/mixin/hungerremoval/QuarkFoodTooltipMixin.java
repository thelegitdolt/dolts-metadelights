package com.dolthhaven.doltmetadelights.core.mixin.hungerremoval;

import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Pseudo
@Mixin(targets = "org.violetmoon.quark.content.client.tooltip.FoodTooltips")
public class QuarkFoodTooltipMixin {
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;"))
    private static String changeToHeartStuff(String path) {
        if (!DMDConfig.COMMON.quarkFoodTooltipBecomesHeart.get()) return path;
        if (!path.contains("/")) return path;

        String iconNameLast = path.substring(path.lastIndexOf("/") + 1);
        String newPath = switch (iconNameLast) {
            case "food_empty_hunger.png" -> "/heart/container.png";
            case "food_half_hunger.png" -> "/heart/poisoned_half.png";
            case "food_full_hunger.png" -> "/heart/poisoned_full.png";
            case "food_empty.png" -> "/heart/container.png";
            case "food_half.png" -> "/heart/half.png";
            case "food_full.png" -> "/heart/full.png";
            default -> null;
        };
        if (newPath != null) return path.substring(0, path.lastIndexOf("/")) + newPath;
        return path;
    }
}
