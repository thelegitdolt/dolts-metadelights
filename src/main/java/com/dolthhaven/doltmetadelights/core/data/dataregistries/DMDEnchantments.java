package com.dolthhaven.doltmetadelights.core.data.dataregistries;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.core.registry.DMDEnchantEffectComponents;
import com.dolthhaven.doltmetadelights.utils.Consts;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableFloat;
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
                .withSpecialEffect(DMDEnchantEffectComponents.THROWN_KNIFE_BONUS_DAMAGE.get(),
                        new AddValue(LevelBasedValue.perLevel(0, 3f)))
                .withSpecialEffect(DMDEnchantEffectComponents.CAN_THROW.get(), Unit.INSTANCE));
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }


    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, DoltsMetadelights.rl(name));
    }

    public static boolean canThrowKnife(ItemStack stack) {
        if (!Consts.DUNGEONS_DELIGHT.loaded()) return false;

        MutableBoolean canThrow = new MutableBoolean(false);
        EnchantmentHelper.runIterationOnItem(stack, (enchant, level) -> {
            if (enchant.value().effects().get(DMDEnchantEffectComponents.CAN_THROW.get()) != null) {
                canThrow.setTrue();
            }
        });
        return canThrow.booleanValue();
    }

    public static float findThrownCleaverAttackDamage(ItemStack stack, RandomSource random) {
        MutableFloat damage = new MutableFloat(0);
        EnchantmentHelper.runIterationOnItem(stack, (enchant, level) -> {
            modifyThrownCleaverAttackDamage(enchant.value(), random, level, damage);
        });
        return damage.getValue();
    }

    private static void modifyThrownCleaverAttackDamage(Enchantment enchantment, RandomSource rand, int level, MutableFloat damage) {
        enchantment.modifyUnfilteredValue(DMDEnchantEffectComponents.THROWN_KNIFE_BONUS_DAMAGE.get(), rand, level, damage);
    }
}
