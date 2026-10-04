package com.dolthhaven.doltmetadelights.core.data.dataregistries;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.core.registry.DMDEnchantmentEffectComponents;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.data.ModEnchantments;

public class DMDEnchantments {
    public static final ResourceKey<Enchantment> BALLISTIC = key("ballistic");

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> itemLookup = context.lookup(Registries.ITEM);
        HolderGetter<Enchantment> enchantLookup = context.lookup(Registries.ENCHANTMENT);
        register(context, BALLISTIC, Enchantment.enchantment(
            Enchantment.definition(
                    itemLookup.getOrThrow(ModTags.Items.KNIFE_ENCHANTABLE),
                    3, // weight
                    3, // max level
                    Enchantment.dynamicCost(15, 9),
                    Enchantment.dynamicCost(50, 8),
                    2, // anvil cost
                    EquipmentSlotGroup.MAINHAND
            )).exclusiveWith(HolderSet.direct(enchantLookup.getOrThrow(ModEnchantments.BACKSTABBING)))
                .withSpecialEffect(DMDEnchantmentEffectComponents.THROWN_KNIFE_DAMAGE.get(),
                        new AddValue(LevelBasedValue.perLevel(6f, 1.5f))));
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }


    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, DoltsMetadelights.rl(name));
    }
}
