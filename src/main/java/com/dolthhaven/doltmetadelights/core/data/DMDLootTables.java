package com.dolthhaven.doltmetadelights.core.data;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.loot.CanItemPerformAbility;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import umpaz.brewinandchewin.common.block.CheeseWheelBlock;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dolthhaven.doltmetadelights.core.registry.DMDBlocks.*;

public class DMDLootTables extends LootTableProvider{
    protected static final LootItemCondition.Builder HAS_KNIFE = MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModTags.Items.KNIVES));
    protected static final LootItemCondition.Builder IS_SHEARS = CanItemPerformAbility.canItemPerformAbility(ItemAbilities.SHEARS_HARVEST);

    static List<Block> BLOCK_BLACKLIST = ImmutableList.of(BOP_GLOWSHROOM_COLONY.get(), TOADSTOOL_COLONY.get());

    public DMDLootTables(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), BuiltInLootTables.all(), ImmutableList.of(
                new LootTableProvider.SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK)
        ), event.getLookupProvider());
    }

    @Override
    protected void validate(WritableRegistry<LootTable> registry, ValidationContext context, ProblemReporter.Collector collector) {
    }

    public static class BlockLoot extends BlockLootSubProvider {
        private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.PIGLIN_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(Collectors.toSet());


        protected BlockLoot(HolderLookup.Provider registries) {
            super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        protected void generate() {
            this.dropSelf(MULCH_BAG.get());
            this.colony(GLOW_SHROOM_COLONY);

            this.cheese(WARDENZOLA);
        }

        @Override
        public Iterable<Block> getKnownBlocks() {
            return BuiltInRegistries.BLOCK.stream().filter(block -> DoltsMetadelights.MOD_ID.equals(BuiltInRegistries.BLOCK.getKey(block).getNamespace())).filter(block -> !BLOCK_BLACKLIST.contains(block)).collect(Collectors.toSet());
        }

        private void colony(Supplier<? extends Block> block) {
            if (block.get() instanceof MushroomColonyBlock colony) {
                Item shroomItem = colony.mushroomType.value();
                Item colonyItem = colony.asItem();
                this.add(block.get(), LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(AlternativesEntry.alternatives(LootItem.lootTableItem(colonyItem)
                                                .when(stateCond(block, MushroomColonyBlock.COLONY_AGE, 3))
                                                .when(IS_SHEARS))
                                        .otherwise(LootItem.lootTableItem(shroomItem)
                                                .apply(MushroomColonyBlock.COLONY_AGE.getPossibleValues(), value -> SetItemCountFunction
                                                        .setCount(ConstantValue.exactly(2.0f + value), false)
                                                        .when(stateCond(block, MushroomColonyBlock.COLONY_AGE, value)))))));
            }
            else {
                throw new IllegalArgumentException("Not mushroom colony");
            }
        }

        private void cheese(Supplier<? extends Block> wheel) {
            if (wheel.get() instanceof CheeseWheelBlock cheese) {
                Item wedge = cheese.cheeseWedgeType.get();
                this.add(wheel.get(), LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(wedge).apply(CheeseWheelBlock.SERVINGS.getPossibleValues(), value -> SetItemCountFunction
                                                .setCount(ConstantValue.exactly(value + 1), false).when(stateCond(wheel, CheeseWheelBlock.SERVINGS, value)))
                                        .when(HAS_KNIFE))));
            }
            else {
                throw new IllegalArgumentException("Not cheese");
            }
        }

        private static <V extends Comparable<V>> LootItemCondition.Builder stateCond(Supplier<? extends Block> block, Property<V> property, V v) {
            return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get())
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, v.toString()));
        }
    }
}
