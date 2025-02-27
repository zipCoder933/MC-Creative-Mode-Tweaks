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

    //Common
    public static final ForgeConfigSpec.IntValue REACH_MIN_RANGE =
            COMMON_BUILDER.comment("Minimum reach distance")
                    .defineInRange("common.REACH_MIN_RANGE", 5, 5, 128);

    public static ForgeConfigSpec.IntValue REACH_MAX_RANGE =
            COMMON_BUILDER.comment("Maximum reach distance")
                    .defineInRange("common.REACH_MAX_RANGE", 128, 5, 256);

    public static final ForgeConfigSpec.IntValue REACH_DEFAULT_RANGE =
            COMMON_BUILDER.comment("Default reach distance when we enter creative mode")
                    .defineInRange("common.REACH_DEFAULT_RANGE", 32, 5, 256);

    //Client
    public static ForgeConfigSpec.IntValue REPLACE_INTERVAL =
            COMMON_BUILDER.comment("The interval in ticks to run replace tool")
                    .defineInRange("client.REPLACE_INTERVAL", 1, 1, 256);

    public static ForgeConfigSpec.BooleanValue INVERT_REPLACE_LOCK =
            COMMON_BUILDER.comment("If we want to invert replace lock. (Default: false)")
                    .define("client.INVERT_REPLACE_LOCK", false);

    private static final ForgeConfigSpec.BooleanValue DISABLE_FLIGHT_INERTIA =
            COMMON_BUILDER.comment("Whether to disable flight inertia")
                    .define("client.DISABLE_FLIGHT_INERTIA", true);

    private static final ForgeConfigSpec.DoubleValue FLIGHT_SPEED =
            COMMON_BUILDER.comment("Flight speed (vanilla is 0.05)")
                    .defineInRange("client.FLIGHT_SPEED", 0.07, 0.05, 1);

    public static final ForgeConfigSpec.BooleanValue NOCLIP_ON_LOGIN =
            COMMON_BUILDER.comment("If No-Clip should be enabled or disabled by default when logging in")
                    .define("client.NOCLIP_ON_LOGIN", true);

    public static final ForgeConfigSpec.BooleanValue DEFAULT_FAST_BLOCK_PLACEMENT =
            COMMON_BUILDER.comment("If fast block placement should be enabled or disabled by default when logging in")
                    .define("client.DEFAULT_FAST_BLOCK_PLACEMENT", true);




    static final ForgeConfigSpec SPEC = COMMON_BUILDER.build();

    //These are the values the mod will read from
    public static boolean disableFlightInertia = false;
    public static float flightSpeed = 0.05f;
    public static boolean fastPlacementEnabled = true;

    //Called when the config is loaded
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        //Its better to set the config values to other variables in here just so that we don't get null pointer exceptions
        disableFlightInertia = Config.DISABLE_FLIGHT_INERTIA.get();
        flightSpeed = (float) ((double) Config.FLIGHT_SPEED.get());
        fastPlacementEnabled = Config.DEFAULT_FAST_BLOCK_PLACEMENT.get();
    }
}
