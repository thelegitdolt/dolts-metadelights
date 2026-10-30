package com.dolthhaven.doltmetadelights.core.other;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.network.codec.ByteBufCodecs;

public class DMDTrackedData {
    public static final TrackedData<Byte> LEVEL_IMPENDING_STAB = TrackedData.Builder.create(ByteBufCodecs.BYTE, () -> (byte) 0).build();
    public static final TrackedData<Byte> LEVEL_DOUBLE_STAB = TrackedData.Builder.create(ByteBufCodecs.BYTE, () -> (byte) 0).build();
    public static final TrackedData<Byte> LEVEL_SATED_BLADE = TrackedData.Builder.create(ByteBufCodecs.BYTE, () -> (byte) 0).build();
    public static final TrackedData<Boolean> IS_DESOLATE_DAGGER = TrackedData.Builder.create(ByteBufCodecs.BOOL, () -> false).build();

    public static void registerTrackedData() {
        TrackedDataManager.INSTANCE.registerData(DoltsMetadelights.rl("desolate"), IS_DESOLATE_DAGGER);
        TrackedDataManager.INSTANCE.registerData(DoltsMetadelights.rl("level_double_stab"), LEVEL_DOUBLE_STAB);
        TrackedDataManager.INSTANCE.registerData(DoltsMetadelights.rl("level_sated_blade"), LEVEL_SATED_BLADE);
        TrackedDataManager.INSTANCE.registerData(DoltsMetadelights.rl("level_impending_stab"), LEVEL_IMPENDING_STAB);
    }
}
