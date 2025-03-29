package org.zipcoder.cmt;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.zipcoder.cmt.network.PacketHandler;
import org.zipcoder.cmt.utils.reach.AdjustRangeHelper;

//https://github.com/Tutorials-By-Kaupenjoe/NeoForge-Tutorial-1.21.X/tree/main/src/main/java/net/kaupenjoe/tutorialmod
// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreativeModeTweaks.MODID)
public class CreativeModeTweaks {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "cmt";

    // Directly reference a slf4j logger
    //The logger is a central point for logging
    private static final Logger LOGGER = LogUtils.getLogger();

    public CreativeModeTweaks(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        modEventBus.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    /**
     * Common setup is run on the client and server
     *
     * @param event
     */
    private void commonSetup(final FMLCommonSetupEvent event) {
        PacketHandler.registerPackets();  // Register network packets
        NeoForge.EVENT_BUS.register(new AdjustRangeHelper());
    }

}
