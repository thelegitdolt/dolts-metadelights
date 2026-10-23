package com.dolthhaven.doltmetadelights.core.event;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.common.block.WardenzolaFluid;
import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.dolthhaven.doltmetadelights.core.registry.DMDBlocks;
import com.dolthhaven.doltmetadelights.intergration.NeapolitanIntegration;
import com.dolthhaven.doltmetadelights.utils.Consts;
import com.dolthhaven.doltmetadelights.utils.RegUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import vectorwing.farmersdelight.data.recipe.CuttingRecipes;

@EventBusSubscriber(modid = DoltsMetadelights.MOD_ID)
public class DMDEvents {
    @SubscribeEvent
    private static void handleBulletPepper(PlayerInteractEvent.RightClickBlock event) {
        if (!DMDConfig.COMMON.killBulletPepperPlacement.get() || !Consts.MY_NETHERS_DELIGHT.loaded())
            return;

        ItemStack stack = event.getItemStack();
        Item bulletPepper = Consts.BULLET_PEPPER.lookup();
        if (bulletPepper != null && stack.is(bulletPepper)) {
            event.setUseItem(TriState.FALSE);
        }
    }

    @SubscribeEvent
    public static void potStrawberry(PlayerInteractEvent.RightClickBlock event) {
        if (Consts.NEAPOLITAN.loaded()) {
            ItemStack stack = event.getItemStack();
            Player player = event.getEntity();
            Level level = event.getLevel();
            BlockPos pos = event.getPos();
            BlockState state = level.getBlockState(pos);

            Item strawberryPip = Consts.STRAWBERRY_PIPS.lookup();
            if (state.is(Blocks.FLOWER_POT) && stack.is(strawberryPip)) {
                BlockState newState = (NeapolitanIntegration.isWhiteStrawberry(level, pos) ?
                        DMDBlocks.POTTED_WHITE_STRAWBERRIES : DMDBlocks.POTTED_STRAWBERRIES).get().defaultBlockState();

                level.setBlock(pos, newState, 3);
                player.awardStat(Stats.POT_FLOWER);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                level.playSound(player, pos, SoundEvents.MOSS_PLACE, SoundSource.BLOCKS);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    private static void changeDefaultItemProperties(ModifyDefaultComponentsEvent event) {
        Consts.DD_WARDENZOLA.safeLookup().ifPresent(item -> event.modify(item, builder -> {
            if (Consts.BnC.loaded()) builder.remove(DataComponents.FOOD);
        }));
    }
}
