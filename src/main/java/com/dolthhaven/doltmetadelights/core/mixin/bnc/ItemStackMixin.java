package com.dolthhaven.doltmetadelights.core.mixin.bnc;

import com.dolthhaven.doltmetadelights.common.entity.ThrownTankardEntity;
import com.dolthhaven.doltmetadelights.core.registry.DMDSounds;
import com.dolthhaven.doltmetadelights.integration.DMDBnCIntegration;
import com.dolthhaven.doltmetadelights.utils.Consts;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// lets you throw tankards
@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void sex(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack handStack = player.getItemInHand(hand);
        if (ModList.get().isLoaded(Consts.BnC) && DMDBnCIntegration.canShootTankard(player, handStack)) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), DMDSounds.TANKARD_SHOOTS.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!level.isClientSide) {
                ThrownTankardEntity tankard = new ThrownTankardEntity(level, player);
                tankard.setItem(handStack);
                tankard.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.8F, 4F);
                level.addFreshEntity(tankard);
            }

            player.awardStat(Stats.ITEM_USED.get(handStack.getItem()));
            if (!player.getAbilities().instabuild) {
                handStack.shrink(1);
            }

            cir.setReturnValue(InteractionResultHolder.sidedSuccess(handStack, level.isClientSide()));
        }
    }
}
