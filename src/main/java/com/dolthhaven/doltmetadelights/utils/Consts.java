package com.dolthhaven.doltmetadelights.utils;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

public class Consts {
    public static final ModId BnC = new ModId("brewinandchewin");
    public static final ModId BOP = new ModId("biomesoplenty");
    public static final ModId DUNGEONS_DELIGHT = new ModId("dungeonsdelight");
    public static final ModId MY_NETHERS_DELIGHT = new ModId("mynethersdelight");
    public static final ModId QUARK = new ModId("quark");


    public static final ResourceLocation BULLET_PEPPER = MY_NETHERS_DELIGHT.rl("bullet_pepper");

    public static final ResourceLocation GLOW_SHROOM  = QUARK.rl("glow_shroom");
    public static final ResourceLocation GLOWSHROOM_BOP = BOP.rl("glowshroom");
    public static final ResourceLocation TOADSTOOL_BOP = BOP.rl("toadstool");
    public static final ResourceLocation DUNGEONS_DELIGHT_TAB = DUNGEONS_DELIGHT.rl("dungeonsdelight_tab");

    public static final ResourceLocation DD_WARDENZOLA = DUNGEONS_DELIGHT.rl("wardenzola");

    public record ModId(String id) {
        public boolean loaded() {
            return ModList.get().isLoaded(id);
        }

        public ResourceLocation rl(String path) {
            return ResourceLocation.fromNamespaceAndPath(id, path);
        }

        public ModLoadedCondition requiresLoaded() {
            return new ModLoadedCondition(id);
        }
    }
}
