package com.dolthhaven.doltmetadelights.core.other;

import net.mehvahdjukaar.moonlight.api.util.DispenserHelper;
import net.mehvahdjukaar.moonlight.api.util.FakePlayerManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class FakePlayerCauldronInteract extends DispenserHelper.AdditionalDispenserBehavior {
    private final CauldronInteraction interaction;

    public FakePlayerCauldronInteract(CauldronInteraction interaction, Item item) {
        super(item);
        this.interaction = interaction;
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> customBehavior(BlockSource source, @NotNull ItemStack stack) {
        ServerLevel level = source.level();
        Direction dir = source.state().getValue(DispenserBlock.FACING);
        BlockPos pos = source.pos().relative(dir);
        Player fp = FakePlayerManager.getDefault(level);

        ItemStack dispenseStack = stack.split(1);
        fp.setItemInHand(InteractionHand.MAIN_HAND, dispenseStack);

        BlockState cauldronState = level.getBlockState(pos);
        ItemInteractionResult result = this.interaction.interact(cauldronState, level, pos, fp, InteractionHand.MAIN_HAND, dispenseStack);

        BlockState newState = level.getBlockState(pos);
        ItemStack remainderStack = fp.getItemInHand(InteractionHand.MAIN_HAND);

        if (newState == cauldronState && !result.consumesAction()) {
            DispenserBlock.DISPENSER_REGISTRY.get(remainderStack.getItem()).dispense(source, remainderStack);
            return InteractionResultHolder.fail(remainderStack);
        } else {
            return result.consumesAction() ? InteractionResultHolder.sidedSuccess(remainderStack, false) : InteractionResultHolder.fail(remainderStack);
        }



    }
}
