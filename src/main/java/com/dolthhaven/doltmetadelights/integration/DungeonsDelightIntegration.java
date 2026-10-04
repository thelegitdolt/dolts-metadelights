package com.dolthhaven.doltmetadelights.integration;

import com.dolthhaven.doltmetadelights.core.data.dataregistries.DMDEnchantments;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.yirmiri.dungeonsdelight.common.entity.misc.CleaverEntity;
import net.yirmiri.dungeonsdelight.common.item.CleaverItem;
import net.yirmiri.dungeonsdelight.core.registry.DDItems;
import net.yirmiri.dungeonsdelight.core.registry.DDSounds;

import java.util.function.Consumer;

public class DungeonsDelightIntegration {
    public static void makeCleaverAndThrowIt(ItemStack stack, Player player, Level level, double attackDamage, Consumer<Entity> postOps) {
        CleaverEntity cleaver = new CleaverEntity(level, player, stack.copy());
        cleaver.setItem(stack.copy());
        ((CleaverItem) DDItems.NETHERITE_CLEAVER.get()).applyEffects(player, stack, cleaver);
        cleaver.setBaseDamage(attackDamage + DMDEnchantments.findThrownCleaverAttackDamage(stack, level.random));

        cleaver.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, ((CleaverItem) DDItems.NETHERITE_CLEAVER.get()).range, 1.0F);
        postOps.accept(cleaver);
        if (player.getAbilities().instabuild) {
            cleaver.pickup = AbstractArrow.Pickup.DISALLOWED;
        }
        level.addFreshEntity(cleaver);
        cleaver.setOwner(player);
        level.playSound(null, cleaver, DDSounds.CLEAVER_THROW.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
    }
}
