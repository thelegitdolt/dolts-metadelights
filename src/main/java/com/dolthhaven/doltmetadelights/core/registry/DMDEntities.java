package com.dolthhaven.doltmetadelights.core.registry;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.common.entity.ThrownTankardEntity;
import com.teamabnormals.blueprint.core.util.registry.EntitySubRegistryHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DMDEntities {
    public static final EntitySubRegistryHelper ENTITY_TYPES = DoltsMetadelights.REGISTRY_HELPER.getEntitySubHelper();

    public static final DeferredHolder<EntityType<?>, EntityType<ThrownTankardEntity>> THROWN_TANKARD = ENTITY_TYPES.createEntity("thrown_tankard", ThrownTankardEntity::new, MobCategory.MISC,
            builder -> builder.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));
}
