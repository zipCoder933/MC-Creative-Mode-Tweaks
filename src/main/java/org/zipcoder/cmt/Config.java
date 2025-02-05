package org.zipcoder.cmt;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = CreativeModeTweaks.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue DISABLE_FLIGHT_INERTIA
            = COMMON_BUILDER.comment("Whether to disable flight inertia")
            .define("disableFlightInertia", true);

    //Block reach
    public static final ForgeConfigSpec.IntValue REACH_MIN_RANGE
            = COMMON_BUILDER.comment("Minimum reach distance")
            .defineInRange("minReach", 5, 5, 128);

    public static ForgeConfigSpec.IntValue REACH_MAX_RANGE =
            COMMON_BUILDER.comment("Minimum reach distance")
                    .defineInRange("common.REACH_MAX_RANGE", 128, 5, 256);

    private static final ForgeConfigSpec.IntValue DEFAULT_REACH_DISTANCE
            = COMMON_BUILDER.comment("Default reach distance when we enter creative mode")
            .defineInRange("defaultReach", 64, 5, 256);


    public static ForgeConfigSpec.IntValue REPLACE_INTERVAL =
            COMMON_BUILDER.defineInRange("client.REPLACE_INTERVAL", 1, 1, 256);

    public static ForgeConfigSpec.BooleanValue INVERT_REPLACE_LOCK =
            COMMON_BUILDER.comment("Default: false").define("client.INVERT_REPLACE_LOCK", false);


    static final ForgeConfigSpec SPEC = COMMON_BUILDER.build();

    //These are the values the mod will read from
    public static boolean disableFlightInertia = false;
    public static int defaultReachDistance = 64;

    //Called when the config is loaded
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        //Its better to set the config values to other variables in here just so that we don't get null pointer exceptions
        disableFlightInertia = Config.DISABLE_FLIGHT_INERTIA.get();
        defaultReachDistance = Config.DEFAULT_REACH_DISTANCE.get();
    }
}
