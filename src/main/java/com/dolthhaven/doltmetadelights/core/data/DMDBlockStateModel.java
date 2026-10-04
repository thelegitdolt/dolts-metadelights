package com.dolthhaven.doltmetadelights.core.data;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.core.registry.DMDBlocks;
import com.teamabnormals.blueprint.core.data.client.BlueprintBlockStateProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;

import java.util.function.Supplier;

import static com.dolthhaven.doltmetadelights.core.registry.DMDBlocks.*;

public class DMDBlockStateModel extends BlueprintBlockStateProvider {
    public DMDBlockStateModel(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), DoltsMetadelights.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void registerStatesAndModels() {
        this.directionalBlock(MULCH_BAG);
        this.colony(GLOW_SHROOM_COLONY);
        this.colony(BOP_GLOWSHROOM_COLONY);
        this.colony(TOADSTOOL_COLONY);
    }

    private void colony(Supplier<Block> blockSupplier) {
        Block block = blockSupplier.get();
        this.getVariantBuilder(block)
                .forAllStates(blockState -> {
                    int age = blockState.getValue(MushroomColonyBlock.COLONY_AGE);
                    return ConfiguredModel.builder().modelFile(this.models()
                            .cross(name(block) + "_stage" + age, blockTexture(block).withSuffix("_stage" + age)).renderType("cutout")).build();
                });
        basicItemWithWeirdPath(id(block).getPath(), blockTexture(block).withSuffix("_stage3"));
    }

    private ResourceLocation id(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private void basicItemWithWeirdPath(String name, ResourceLocation location) {
        this.itemModels().getBuilder(name).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", location);
    }
}
