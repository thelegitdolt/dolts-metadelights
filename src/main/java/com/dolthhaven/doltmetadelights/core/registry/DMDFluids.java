package com.dolthhaven.doltmetadelights.core.registry;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.common.block.WardenzolaFluid;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class DMDFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, DoltsMetadelights.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, DoltsMetadelights.MOD_ID);

    public static final DeferredHolder<FluidType, FluidType> WARDENZOLA_FLUID_TYPE = FLUID_TYPES.register("wardenzola_fluid_type", WardenzolaFluid::new);
    public static final DeferredHolder<Fluid, FlowingFluid> WARDENZOLA_SOURCE = FLUIDS.register("wardenzola", () ->
            new BaseFlowingFluid.Source(DMDFluids.WARDENZOLA_FLUID_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> WARDENZOLA_FLOWING = FLUIDS.register("flowing_wardenzola", () ->
            new BaseFlowingFluid.Flowing(DMDFluids.WARDENZOLA_FLUID_PROPERTIES));

    public static final BaseFlowingFluid.Properties WARDENZOLA_FLUID_PROPERTIES = new BaseFlowingFluid.Properties
            (WARDENZOLA_FLUID_TYPE, WARDENZOLA_SOURCE, WARDENZOLA_FLOWING);
}
