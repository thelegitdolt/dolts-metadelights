package com.dolthhaven.doltmetadelights.core.registry;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.mojang.serialization.MapCodec;
import com.teamabnormals.blueprint.core.api.conditions.ConfigValueCondition;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class DMDLootConditions {
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS = DeferredRegister.create(NeoForgeRegistries.CONDITION_SERIALIZERS, DoltsMetadelights.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends ICondition>, ConfigValueCondition.Serializer> CONFIG = CONDITIONS.register("config",
             () -> new ConfigValueCondition.Serializer(DataUtil.getConfigValues(DMDConfig.COMMON)));
}
