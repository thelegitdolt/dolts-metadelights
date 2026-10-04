package com.dolthhaven.doltmetadelights.common.block;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;

public class WardenzolaFluid extends FluidType {
    public static final ResourceLocation WARDENZOLA_FLOWING_TEXTURE = DoltsMetadelights
            .rl("block/wardenzola_flowing");
    public static final ResourceLocation WARDENZOLA_STILL_TEXTURE = DoltsMetadelights
            .rl("block/wardenzola_still");

    public WardenzolaFluid() {
        super(Properties.create().sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH));
    }
}

