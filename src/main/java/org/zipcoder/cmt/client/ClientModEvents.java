package org.zipcoder.cmt.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;
import org.zipcoder.cmt.client.keys.AdjustRangeKey;
import org.zipcoder.cmt.client.keys.ReplaceKey;

import static org.zipcoder.cmt.CreativeModeTweaks.MODID;

// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    public static final String DEFAULT_CATEGORY = "key." + MODID + ".default";

    public static final KeyMapping KEY_TOGGLE_NOCLIP = new KeyMapping(
            "key." + MODID + ".toggle_noclip",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            DEFAULT_CATEGORY
    );

    public static final KeyMapping KEY_REPLACE = new ReplaceKey("key." + MODID + ".replace",
            GLFW.GLFW_KEY_UNKNOWN, DEFAULT_CATEGORY);

    public static final KeyMapping KEY_ADJUSTRANGE = new AdjustRangeKey("key." + MODID + ".adjustrange",
            GLFW.GLFW_KEY_UNKNOWN, DEFAULT_CATEGORY);

    /**
     * Setup the client side.
     *
     * @param event
     */
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(KEY_REPLACE);
        MinecraftForge.EVENT_BUS.register(KEY_ADJUSTRANGE);
    }


    @SubscribeEvent
    public static void onRegisterGuiOverlaysEvent(RegisterGuiOverlaysEvent event) {
        if (KEY_ADJUSTRANGE instanceof IGuiOverlay)
            event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), KEY_ADJUSTRANGE.getName(), (IGuiOverlay) KEY_ADJUSTRANGE);

        if (KEY_REPLACE instanceof IGuiOverlay)
            event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), KEY_REPLACE.getName(), (IGuiOverlay) KEY_REPLACE);

        if (KEY_TOGGLE_NOCLIP instanceof IGuiOverlay)
            event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), KEY_TOGGLE_NOCLIP.getName(), (IGuiOverlay) KEY_TOGGLE_NOCLIP);
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KEY_TOGGLE_NOCLIP);
        event.register(KEY_REPLACE);
        event.register(KEY_ADJUSTRANGE);
    }
}