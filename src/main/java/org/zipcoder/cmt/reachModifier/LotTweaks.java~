package org.zipcoder.cmt.reachModifier;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.zipcoder.cmt.reachModifier.client.LotTweaksClient;
import org.zipcoder.cmt.reachModifier.network.LTPacketHandler;

public class LotTweaks {
    public static final String NAME = "LotTweaks";

    public LotTweaks(IEventBus modEventBus) {
        modEventBus.addListener(this::clientInit);
        modEventBus.addListener(this::commonInit);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(LotTweaksClient::onRegisterKeyMappingsEvent);
            modEventBus.addListener(LotTweaksClient::onRegisterGuiOverlaysEvent);
        }
    }

    private void clientInit(FMLClientSetupEvent event) {
        LotTweaksClient.init();
    }

    private void commonInit(FMLCommonSetupEvent event) {
        LTPacketHandler.init();
        MinecraftForge.EVENT_BUS.register(new AdjustRangeHelper());
    }

}