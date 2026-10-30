package com.dolthhaven.doltmetadelights.utils;

import com.dolthhaven.doltmetadelights.core.data.DummyItemsRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import javax.swing.text.html.Option;
import java.util.Objects;
import java.util.Optional;

public class Consts {
    public static final ModId ABNORMALS_DELIGHT = new ModId("abnormals_delight");
    public static final ModId AMENDMENTS = new ModId("amendments");
    public static final ModId ALEXS_CAVES = new ModId("alexscaves");
    public static final ModId BnC = new ModId("brewinandchewin");
    public static final ModId BOP = new ModId("biomesoplenty");
    public static final ModId DUNGEONS_DELIGHT = new ModId("dungeonsdelight");
    public static final ModId MY_NETHERS_DELIGHT = new ModId("mynethersdelight");
    public static final ModId NEAPOLITAN = new ModId("neapolitan");
    public static final ModId QUARK = new ModId("quark");
    public static final ModId SUPPLEMENTARIES = new ModId("supplementaries");


    public static final ModResource BULLET_PEPPER = MY_NETHERS_DELIGHT.resource("bullet_pepper");

    public static final ModResource GLOW_SHROOM  = QUARK.registrableResource("glow_shroom");
    public static final ModResource GLOWSHROOM_BOP = BOP.registrableResource("glowshroom");
    public static final ModResource TOADSTOOL_BOP = BOP.registrableResource("toadstool");
    public static final ModResource STRAWBERRY_PIPS = NEAPOLITAN.registrableResource("strawberry_pips");
    public static final ModResource STRAWBERRY_BUSH = NEAPOLITAN.resource("strawberry_bush");

    public static final ResourceLocation DUNGEONS_DELIGHT_TAB = DUNGEONS_DELIGHT.rl("dungeonsdelight_tab");

    public static final ModResource DD_WARDENZOLA = DUNGEONS_DELIGHT.resource("wardenzola");

    public static final ModResource DESOLATE_DAGGER = ALEXS_CAVES.resource("desolate_dagger");

    public static final ResourceLocation IMPENDING_STAB = ALEXS_CAVES.rl("impending_stab");
    public static final ResourceLocation SATED_BLADE = ALEXS_CAVES.rl("sated_blade");
    public static final ResourceLocation DOUBLE_STAB = ALEXS_CAVES.rl("double_stab");

    public record ModId(String id) {
        public boolean loaded() {
            return ModList.get().isLoaded(id);
        }

        public ResourceLocation rl(String path) {
            return ResourceLocation.fromNamespaceAndPath(id, path);
        }

        public MutableComponent translatable(String path) {
            return Component.translatable(path.formatted(id));
        }

        public ModLoadedCondition requiresLoaded() {
            return new ModLoadedCondition(id);
        }

        public ModResource resource(String path) {
            return new ModResource(rl(path));
        }

        public ModResource registrableResource(String path) {
            var source = new ModResource(rl(path));
            DummyItemsRegistry.DUMMY_IDS.add(source);
            return source;
        }
    }

    public record ModResource(ResourceLocation loc) {
        public boolean exists() {
            return ModList.get().isLoaded(loc.getNamespace());
        }

        public Item item() {
            return Objects.requireNonNull(RegUtil.item(loc));
        }

        public Optional<Item> item$() {
            return Optional.ofNullable(RegUtil.item(loc));
        }

        public Block block() {
            return RegUtil.block(loc);
        }

        public Holder<Item> itemHolder() {
            return RegUtil.itemHolder(loc);
        }

        public Holder<Block> blockHolder() {
            return RegUtil.blockHolder(loc);
        }

        public <V> V as(Registry<V> register) {
            return register.get(loc);
        }

        public boolean is(Entity entity) {
            return RegUtil.entityId(entity.getType()).equals(loc());
        }

        public boolean is(ItemStack stack) {
            Item item = item();
            if (item == null) return false;
            return stack.is(item);
        }

        public boolean is(BlockState state) {
            Block block = block();
            if (block == null) return false;
            return state.is(block());
        }
    }
}
