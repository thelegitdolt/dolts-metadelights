package com.dolthhaven.doltmetadelights.core.registry;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Unit;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DMDEnchantEffectComponents {
    public static final DeferredRegister.DataComponents ENCHANTMENT_COMPONENT_TYPES = DeferredRegister.createDataComponents(
            Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, DoltsMetadelights.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<EnchantmentValueEffect>> THROWN_KNIFE_BONUS_DAMAGE = ENCHANTMENT_COMPONENT_TYPES
            .registerComponentType("knife_bonus_damage", builder ->
                    builder.persistent(EnchantmentValueEffect.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> CAN_THROW = ENCHANTMENT_COMPONENT_TYPES
            .registerComponentType("can_throw_knife", builder ->
                    builder.persistent(Unit.CODEC));
}
