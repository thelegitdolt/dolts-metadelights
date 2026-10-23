package com.dolthhaven.doltmetadelights.core.event;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.core.other.DMDConfig;
import com.dolthhaven.doltmetadelights.core.registry.DMDBlocks;
import com.dolthhaven.doltmetadelights.integration.AmendmentDoubleCakeUtil;
import com.dolthhaven.doltmetadelights.integration.NeapolitanIntegration;
import com.dolthhaven.doltmetadelights.utils.Consts;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import vectorwing.farmersdelight.common.tag.ModTags;

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
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (Consts.AMENDMENTS.loaded()) return;
        BlockState state = event.getState();
        Player player = event.getPlayer();
        if (player.getMainHandItem().is(ModTags.Items.KNIVES) && AmendmentDoubleCakeUtil.isDoubleCakeBlock(state)) {
            var cakeAndSlice = AmendmentDoubleCakeUtil.getCakeAndSlice(state);
            if (cakeAndSlice == null) return;

            int bites = state.getValue(CakeBlock.BITES);

            Block.popResource((Level) event.getLevel(), event.getPos(), new ItemStack(cakeAndSlice.getSecond(), 14 - bites));
        }
    }

    @SubscribeEvent
    private static void changeDefaultItemProperties(ModifyDefaultComponentsEvent event) {
        Consts.DD_WARDENZOLA.safeLookup().ifPresent(item -> event.modify(item, builder -> {
            if (Consts.BnC.loaded()) builder.remove(DataComponents.FOOD);
        }));

        int[] numbers = {1, 1, 1, 2, 3, 4, 4, 5, 6, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

        for (Item item : BuiltInRegistries.ITEM.stream().toList()) {
            FoodProperties food = item.getDefaultInstance().get(DataComponents.FOOD);
            if (food != null) {
                int nut = food.nutrition();
                if (nut > 0 && nut <= 21) {
                    event.modify(item, builder -> {
                        builder.set(DataComponents.FOOD, copyWithNutrition(food, numbers[nut - 1]));
                    });
                }
            }
        }
    }

    private static FoodProperties copyWithNutrition(FoodProperties properties, int newNut) {
        return new FoodProperties(newNut, properties.saturation(), properties.canAlwaysEat(), properties.eatSeconds(), properties.usingConvertsTo(), properties.effects());
    }
}
