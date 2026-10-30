package com.dolthhaven.doltmetadelights.core.mixin;

import com.dolthhaven.doltmetadelights.core.data.dataregistries.DMDEnchantments;
import com.dolthhaven.doltmetadelights.core.other.DMDTrackedData;
import com.dolthhaven.doltmetadelights.integration.DungeonsDelightIntegration;
import com.dolthhaven.doltmetadelights.utils.Consts;
import com.dolthhaven.doltmetadelights.utils.RegUtil;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.Optional;

@Mixin(Item.class)
public abstract class MakeKnifeThrowableItemMixin {
    @Shadow
    public abstract int getUseDuration(ItemStack stack, LivingEntity entity);

    @Inject(method = "getUseAnimation", at = @At("HEAD"), cancellable = true)
    private void DoltModHow$CanThrowKnife(ItemStack stack, CallbackInfoReturnable<UseAnim> cir) {
        if (DMDEnchantments.canThrowKnife(stack)) {
            cir.setReturnValue(UseAnim.BOW);
        }
    }

    @Inject(method = "onUseTick", at = @At("HEAD"))
    private void DoltModHow$CanThrowKnife(Level level, LivingEntity entity, ItemStack stack, int timeLeft, CallbackInfo ci) {
        if (DMDEnchantments.canThrowKnife(stack) && entity instanceof Player player) {
            int usedTicks = this.getUseDuration(stack, entity) - timeLeft;
            if (usedTicks == 32) {
                DungeonsDelightIntegration.playCleaverReadySound(level, player);
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

        if (entity instanceof Player player && stack.getItem() instanceof TieredItem knifeItem) {
            boolean isDesolateDagger = RegUtil.itemId(stack.getItem()).equals(Consts.DESOLATE_DAGGER.loc());
            if (!(knifeItem instanceof KnifeItem || isDesolateDagger)) return;

            if (knifeItem.getUseDuration(stack, entity) - timeLeft >= 6 && !player.getCooldowns().isOnCooldown(stack.getItem())) {
                if (!level.isClientSide) {
                    DungeonsDelightIntegration.makeCleaverAndThrowIt(stack, player, level, knifeItem.getTier().getAttackDamageBonus(), cleaver -> {
                        if (isDesolateDagger) {
                            saveToCleaver(cleaver, stack, level);
                        }
                    });
                }
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
        }
    }

    @Unique
    private static void saveToCleaver(Entity entity, ItemStack stack, Level level) {
        IDataManager cleaverData = (IDataManager) entity;
        cleaverData.setValue(DMDTrackedData.IS_DESOLATE_DAGGER, true);
        cleaverData.setValue(DMDTrackedData.LEVEL_IMPENDING_STAB, (byte) getEnchantmentLevel(level, stack, Consts.IMPENDING_STAB));
        cleaverData.setValue(DMDTrackedData.LEVEL_DOUBLE_STAB, (byte) getEnchantmentLevel(level, stack, Consts.DOUBLE_STAB));
        cleaverData.setValue(DMDTrackedData.LEVEL_SATED_BLADE, (byte) getEnchantmentLevel(level, stack, Consts.SATED_BLADE));
    }

    @Unique
    private static int getEnchantmentLevel(Level level, ItemStack stack, ResourceLocation enchantmentKey) {
        if (level != null && stack != null && !stack.isEmpty()) {
            Optional<Registry<Enchantment>> lookup = level.registryAccess().registry(Registries.ENCHANTMENT);
            if (lookup.isEmpty()) {
                return 0;
            } else {
                Optional<Holder.Reference<Enchantment>> holder = lookup.get().getHolder(enchantmentKey);
                return holder.map(stack::getEnchantmentLevel).orElse(0);
            }
        } else {
            return 0;
        }
    }

}
