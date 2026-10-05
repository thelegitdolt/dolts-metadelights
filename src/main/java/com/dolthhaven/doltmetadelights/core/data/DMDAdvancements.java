package com.dolthhaven.doltmetadelights.core.data;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.utils.Consts;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import umpaz.brewinandchewin.common.registry.BnCEffects;
import umpaz.brewinandchewin.common.registry.BnCItems;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DMDAdvancements implements AdvancementProvider.AdvancementGenerator {
    public static AdvancementProvider create(GatherDataEvent event, CompletableFuture<HolderLookup.Provider> lookup) {
        return new AdvancementProvider(event.getGenerator().getPackOutput(), lookup, event.getExistingFileHelper(), List.of(new DMDAdvancements()));
    }

    @Override
    public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
        createAdvancement("dui", "bnc", Consts.BnC.rl("main/brew_drink"), () -> BnCItems.BEER, AdvancementType.GOAL,  true, true, false)
                .addCriterion("bro_is_drunk", CriteriaTriggers.TICK.createCriterion(
                        new PlayerTrigger.TriggerInstance(Optional.of(ContextAwarePredicate
                                .create(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                                        reqEffectAndAmplifier(BnCEffects.TIPSY, MinMaxBounds.Ints.atLeast(4)).build()).build(),
                                        LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity()
                                                .vehicle(EntityPredicate.Builder.entity())).build()
                                )
                        )))).save(consumer, DoltsMetadelights.rl("bnc/dui"), existingFileHelper);
    }

    private static Advancement.Builder createAdvancement(String name, String category, ResourceLocation parent, Supplier<Item> icon, AdvancementType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return Advancement.Builder.advancement().parent(Advancement.Builder.advancement().build(parent)).display(icon.get(),
                DoltsMetadelights.WRAPPED_ID.translatable("advancements.%s." + category + "." + name + ".title"),
                DoltsMetadelights.WRAPPED_ID.translatable("advancements.%s." + category + "." + name + ".description"),
                null, frame, showToast, announceToChat, hidden);
    }

    private static EntityPredicate.Builder reqEffectAndAmplifier(Holder<MobEffect> effect, MinMaxBounds.Ints bound) {
        return EntityPredicate.Builder.entity().effects(new MobEffectsPredicate.Builder().and(BnCEffects.TIPSY, new MobEffectsPredicate.MobEffectInstancePredicate(bound, MinMaxBounds.Ints.ANY, Optional.empty(), Optional.empty())));
    }
}
