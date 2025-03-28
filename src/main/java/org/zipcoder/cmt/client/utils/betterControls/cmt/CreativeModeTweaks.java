package org.zipcoder.cmt.client.utils.betterControls.cmt;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.zipcoder.cmt.client.utils.betterControls.cmt.network.PacketHandler;
import org.zipcoder.cmt.client.utils.betterControls.cmt.utils.reach.AdjustRangeHelper;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreativeModeTweaks.MODID)
public class CreativeModeTweaks {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "cmt";

    // Directly reference a slf4j logger
    //The logger is a central point for logging
    private static final Logger LOGGER = LogUtils.getLogger();

    public CreativeModeTweaks() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    /**
     * Common setup is run on the client and server
     *
     * @param event
     */
    private void commonSetup(final FMLCommonSetupEvent event) {
        PacketHandler.registerPackets();  // Register network packets
        MinecraftForge.EVENT_BUS.register(new AdjustRangeHelper());
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }


}
