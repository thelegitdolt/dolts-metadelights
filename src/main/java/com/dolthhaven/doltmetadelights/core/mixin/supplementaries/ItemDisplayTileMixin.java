package com.dolthhaven.doltmetadelights.core.mixin.supplementaries;

import net.mehvahdjukaar.moonlight.api.block.ItemDisplayTile;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemDisplayTile.class)
public class ItemDisplayTileMixin {
//    @WrapOperation(method = "interact(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;I)Lnet/minecraft/world/InteractionResult;",
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setItemInHand(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;)V"))
//    private void DoltModHow$FlowerPotPutStuffBack(Player instance, InteractionHand interactionHand, ItemStack itemStack, Operation<Void> original) {
//        DoltUtils.addToInvOrDrop(instance, itemStack);
//    }

}
