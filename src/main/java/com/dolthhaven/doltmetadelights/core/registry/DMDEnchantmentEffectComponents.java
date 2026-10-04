package com.dolthhaven.doltmetadelights.core.registry;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DMDEnchantmentEffectComponents {
    public static final DeferredRegister.DataComponents ENCHANTMENT_COMPONENT_TYPES = DeferredRegister.createDataComponents(
            Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, DoltsMetadelights.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<EnchantmentValueEffect>> THROWN_KNIFE_DAMAGE = ENCHANTMENT_COMPONENT_TYPES
            .registerComponentType("makes_knives_throwable", builder ->
                    builder.persistent(EnchantmentValueEffect.CODEC));
}
