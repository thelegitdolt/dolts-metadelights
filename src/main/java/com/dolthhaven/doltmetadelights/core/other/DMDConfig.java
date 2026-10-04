package com.dolthhaven.doltmetadelights.core.other;

import com.teamabnormals.blueprint.core.annotations.ConfigKey;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import static net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class DMDConfig {
    public static class Common {
        @ConfigKey("placeable_wardenzola")
        public final ConfigValue<Boolean> wheelifiedWardenzola;
        public final ConfigValue<Boolean> conqueringStar;

        public final ConfigValue<Boolean> doRichSoilGrowFungusColony;
        public final ConfigValue<Boolean> killBulletPepperPlacement;
        public final ConfigValue<Boolean> frogsAreNotStupid;
        public final ConfigValue<Boolean> ghastaWithCreamDoesntRegenerate;
        public final ConfigValue<Boolean> hoglinMountDoesntTick;

        Common(ModConfigSpec.Builder builder) {

            builder.push("farmersdelight");
            builder.push("Ballistic");
            conqueringStar = builder.comment("If knives can receive the Ballistic enchantment, which gives them the effect of Dungeon's Delight cleavers to be thrown. REQUIRES DUNGEON's DELIGHT.").define("Cleaverfication Enchantment", false);
            builder.pop();
            builder.pop();


            builder.push("nethersdelight");

            builder.push("fungus_colonies");
            doRichSoilGrowFungusColony = builder.comment("If fungus colonies should grow on normal rich soil instead of soul rich soil").define("Rich Fungus", true);
            builder.pop();

            builder.push("bullet_peppers");
            killBulletPepperPlacement = builder.comment("If bullet peppers should become unplaceable, thus killing letios plants forever").define("Kill letios plants", true);
            builder.pop();

            builder.push("magma_cakes");
            frogsAreNotStupid = builder.comment("If frogs should become unable to consume magma cakes").define("Magma Cakes Good", false);
            builder.pop();

            builder.push("ghasta");
            ghastaWithCreamDoesntRegenerate = builder.comment("If the ghasta with cream should be stopped from regenerating itself").define("Be normal ghasta", false);
            builder.pop();

            builder.push("hoglin mounts");
            hoglinMountDoesntTick = builder.comment("If hoglin mounts should never zombify and whatever").define("Abnormal Hoglins", false);
            builder.pop();

            builder.pop();


            builder.push("Dungeon's Delight");

            builder.push("Brewing and Chewing Wardenzola");
            wheelifiedWardenzola = builder.comment("If Wardenzola Dungeons Delight Should be Brewing and Chewingified; this means that they are placeable and have a keg recipe, as well as wedges.")
                    .define("Wardenzola Wheel", false);
            builder.pop();

            builder.pop();
        }
    }

    public static final ModConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(DMDConfig.Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
