package com.dolthhaven.doltmetadelights.core.mixin;

import com.dolthhaven.doltmetadelights.core.data.dataregistries.DMDEnchantments;
import com.dolthhaven.doltmetadelights.integration.DungeonsDelightIntegration;
import com.dolthhaven.doltmetadelights.utils.Consts;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.yirmiri.dungeonsdelight.core.registry.DDSounds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.item.KnifeItem;

@Mixin(Item.class)
public abstract class MakeKnifeThrowableItemMixin {
    @Shadow
    public abstract int getUseDuration(ItemStack stack, LivingEntity entity);

    @Inject(method = "getUseAnimation", at = @At("HEAD"), cancellable = true)
    private void hi(ItemStack stack, CallbackInfoReturnable<UseAnim> cir) {
        if (DMDEnchantments.canThrowKnife(stack)) {
            cir.setReturnValue(UseAnim.BOW);
        }
    }

    @Inject(method = "onUseTick", at = @At("HEAD"))
    private void sex(Level level, LivingEntity entity, ItemStack stack, int timeLeft, CallbackInfo ci) {
        if (DMDEnchantments.canThrowKnife(stack) && entity instanceof Player player) {
            int usedTicks = this.getUseDuration(stack, entity) - timeLeft;
            if (usedTicks == 32) {
                level.playSound(null, player, DDSounds.CLEAVER_READY.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
    }

    @Inject(method = "getUseDuration", at = @At("HEAD"), cancellable = true)
    private void DMH$BallisticUseDuration(ItemStack stack, LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        if (DMDEnchantments.canThrowKnife(stack)) {
            cir.setReturnValue(72000);
        }
    }

    @Inject(method = "releaseUsing", at = @At("HEAD"))
    private void DMH$BallisticReleaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft, CallbackInfo ci) {
        if (DMDEnchantments.canThrowKnife(stack)) {
            throwCleaver(level, entity, stack, timeLeft);
        }
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void DMH$UseBallisticKnife(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack stack = player.getItemInHand(hand);
        if (DMDEnchantments.canThrowKnife(stack)) {
            if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
                cir.setReturnValue(InteractionResultHolder.fail(stack));
            }
            player.startUsingItem(hand);
            cir.setReturnValue(InteractionResultHolder.consume(stack));
        }
    }

    @Unique
    private void throwCleaver(Level level, LivingEntity entity, ItemStack stack, int timeLeft) {
        assert Consts.DUNGEONS_DELIGHT.loaded();

        if (entity instanceof Player player && stack.getItem() instanceof KnifeItem knifeItem) {
            if (knifeItem.getUseDuration(stack, entity) - timeLeft >= 6 && !player.getCooldowns().isOnCooldown(stack.getItem())) {
                if (!level.isClientSide) {
                    DungeonsDelightIntegration.makeCleaverAndThrowIt(stack, player, level, knifeItem.getTier().getAttackDamageBonus(), cleaver -> {});
                }
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
        }
    }

}
