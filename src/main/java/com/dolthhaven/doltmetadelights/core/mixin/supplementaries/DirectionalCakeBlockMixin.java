package com.dolthhaven.doltmetadelights.core.mixin.supplementaries;

import com.dolthhaven.doltmetadelights.core.data.tag.DMDDataMaps;
import com.dolthhaven.doltmetadelights.integration.AmendmentDoubleCakeUtil;
import com.dolthhaven.doltmetadelights.utils.RegUtil;
import com.mojang.datafixers.util.Pair;
import net.mehvahdjukaar.amendments.common.block.DoubleCakeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.utility.ItemUtils;

@Pseudo
@Mixin(DoubleCakeBlock.class)
public class DirectionalCakeBlockMixin {
    // outputs the cake block and also slice item, null otherwise
    @Unique


    @Inject(method = "useItemOn", at = @At("HEAD"), remap = false, cancellable = true)
    private void hi(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<ItemInteractionResult> cir){
        var cakePair = AmendmentDoubleCakeUtil.getCakeAndSlice(state);
        if (cakePair == null) return;

        int bites = state.getValue(CakeBlock.BITES);
        if (bites < 6) {
            level.setBlock(pos, state.setValue(CakeBlock.BITES, bites + 1), 3);
        } else {
            level.setBlock(pos, cakePair.getFirst(), 3);
        }

        ItemUtils.spawnItemEntity(level, cakePair.getSecond().getDefaultInstance(),
                pos.getX() + bites * 0.1,
                pos.getY() + 0.4,
                pos.getZ() + 0.5F, -0.02, 0, 0);

        level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
        cir.setReturnValue(ItemInteractionResult.sidedSuccess(level.isClientSide));
    }
}

