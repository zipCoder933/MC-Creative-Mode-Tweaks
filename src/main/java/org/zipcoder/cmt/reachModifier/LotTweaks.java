package org.zipcoder.cmt.reachModifier;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.zipcoder.cmt.reachModifier.network.LTPacketHandler;

public class LotTweaks {


    public LotTweaks(IEventBus modEventBus) {
        modEventBus.addListener(this::commonInit);
    }

    private void commonInit(FMLCommonSetupEvent event) {
        LTPacketHandler.init();
        MinecraftForge.EVENT_BUS.register(new AdjustRangeHelper());
    }

}