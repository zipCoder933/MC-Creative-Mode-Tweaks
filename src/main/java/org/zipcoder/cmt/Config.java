package org.zipcoder.cmt;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = CreativeModeTweaks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue DISABLE_FLIGHT_INERTIA
            = COMMON_BUILDER.comment("Whether to disable flight inertia")
            .define("disableFlightInertia", true);

    private static final ForgeConfigSpec.IntValue MIN_REACH_DISTANCE
            = COMMON_BUILDER.comment("Minimum reach distance")
            .defineInRange("minReach", 5, 5, 128);

    private static final ForgeConfigSpec.IntValue MAX_REACH_DISTANCE
            = COMMON_BUILDER.comment("Maximum reach distance")
            .defineInRange("maxReach", 5, 5, 256);

    private static final ForgeConfigSpec.IntValue DEFAULT_REACH_DISTANCE
            = COMMON_BUILDER.comment("Default reach distance when we enter creative mode")
            .defineInRange("defaultReach", 64, 5, 256);

    static final ForgeConfigSpec SPEC = COMMON_BUILDER.build();

    //These are the values the mod will read from
    public static boolean disableFlightInertia = false;
    public static int minReachDistance = 5;
    public static int maxReachDistance = 128;
    public static int defaultReachDistance = 64;

    //Called when the config is loaded
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        //Its better to set the config values to other variables in here just so that we don't get null pointer exceptions
        disableFlightInertia = Config.DISABLE_FLIGHT_INERTIA.get();
        minReachDistance = Config.MIN_REACH_DISTANCE.get();
        maxReachDistance = Config.MAX_REACH_DISTANCE.get();
        defaultReachDistance = Config.DEFAULT_REACH_DISTANCE.get();
    }
}
