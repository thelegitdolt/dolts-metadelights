package com.dolthhaven.doltmetadelights.client;

import com.dolthhaven.doltmetadelights.DoltsMetadelights;
import com.dolthhaven.doltmetadelights.common.block.WardenzolaFluid;
import com.dolthhaven.doltmetadelights.core.registry.DMDEntities;
import com.dolthhaven.doltmetadelights.core.registry.DMDFluids;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = DoltsMetadelights.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onEntityRendererRegister(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DMDEntities.THROWN_TANKARD.get(), ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            public ResourceLocation getStillTexture() {
                return WardenzolaFluid.WARDENZOLA_STILL_TEXTURE;
            }

            public ResourceLocation getFlowingTexture() {
                return WardenzolaFluid.WARDENZOLA_FLOWING_TEXTURE;
            }
        }, DMDFluids.WARDENZOLA_FLUID_TYPE.get());
    }
}
