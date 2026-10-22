package com.dolthhaven.doltmetadelights.core.mixin.supplementaries;

import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import net.mehvahdjukaar.supplementaries.common.items.QuiverItem;
import net.mehvahdjukaar.supplementaries.common.items.components.QuiverContent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.utility.ItemUtils;

/**
 * Mixins cutting boards so that when they process quivers, they will always drop every arrow they have inside it.
 */
@Pseudo
@Mixin(CuttingBoardBlockEntity.class)
public abstract class CuttingBoardBlockEntityMixin extends SyncedBlockEntity {
    @Shadow
    @Final
    private ItemStackHandler inventory;

    public CuttingBoardBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Inject(method = "lambda$processStoredItemUsingTool$2", at = @At("HEAD"), remap = false)
    private void DoltModHow$ProcessQuiver(ItemStack toolStack, Player player, RecipeHolder recipe, CallbackInfo ci) {
        assert this.level != null;
        ItemStack boardStack = this.inventory.getStackInSlot(0);
        if (boardStack.getItem() instanceof QuiverItem quiverItem) {
            QuiverContent quiverContent = boardStack.get(quiverItem.getComponentType());
            if (quiverContent == null) return;

            for (ItemStack resultStack : quiverContent.getContentCopy()) {
                Direction direction = this.getBlockState().getValue(CuttingBoardBlock.FACING).getCounterClockWise().getOpposite();
                ItemUtils.spawnItemEntity(this.level, resultStack.copy(), (double) this.worldPosition.getX() + 0.5 + (double) direction.getStepX() * 0.2, (double) this.worldPosition.getY() + 0.2, (double) this.worldPosition.getZ() + 0.5 + (double) direction.getStepZ() * 0.2, (float) direction.getStepX() * 0.2F, 0.0, (float) direction.getStepZ() * 0.2F);
            }
        }
    }
}
