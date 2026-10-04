package com.dolthhaven.doltmetadelights.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.Optional;

public class Consts {
    public static final ModId BnC = new ModId("brewinandchewin");
    public static final ModId BOP = new ModId("biomesoplenty");
    public static final ModId DUNGEONS_DELIGHT = new ModId("dungeonsdelight");
    public static final ModId MY_NETHERS_DELIGHT = new ModId("mynethersdelight");
    public static final ModId QUARK = new ModId("quark");


    public static final ItemResource BULLET_PEPPER = MY_NETHERS_DELIGHT.itemResource("bullet_pepper");

    public static final ItemResource GLOW_SHROOM  = QUARK.itemResource("glow_shroom");
    public static final ItemResource GLOWSHROOM_BOP = BOP.itemResource("glowshroom");
    public static final ItemResource TOADSTOOL_BOP = BOP.itemResource("toadstool");
    public static final ResourceLocation DUNGEONS_DELIGHT_TAB = DUNGEONS_DELIGHT.rl("dungeonsdelight_tab");

    public static final ItemResource DD_WARDENZOLA = DUNGEONS_DELIGHT.itemResource("wardenzola");

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

        public ItemResource itemResource(String path) {
            return new ItemResource(rl(path));
        }

        public BlockResource blockResource(String path) {
            return new BlockResource(rl(path));
        }
    }

    public record ItemResource(ResourceLocation loc) {
        public Item lookup() {
            return RegUtil.item(loc);
        }

        public Optional<Item> safeLookup() {
            return Optional.ofNullable(lookup());
        }

        public Block getBlock() {
            return RegUtil.block(loc);
        }

        public Optional<Block> safeGetBlock() {
            return Optional.ofNullable(getBlock());
        }
    }

    public record BlockResource(ResourceLocation loc) {
        public Block lookup() {
            return RegUtil.block(loc);
        }

        public Optional<Block> safeLookup() {
            return Optional.ofNullable(lookup());
        }

        public Item getItem() {
            return RegUtil.item(loc);
        }

        public Optional<Item> safeGetItem() {
            return Optional.ofNullable(getItem());
        }
    }
}
