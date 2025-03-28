package org.zipcoder.cmt.client.utils.betterControls.cmt.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.zipcoder.cmt.client.utils.betterControls.cmt.client.utils.ClientUtils;
import org.zipcoder.cmt.client.utils.betterControls.cmt.client.utils.NoClipHandler;

import static org.zipcoder.cmt.client.ClientModEvents.KEY_TOGGLE_NOCLIP;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class KeyInputHandlerClient {


    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KEY_TOGGLE_NOCLIP.isDown()) {
            /**
             * If we are in creative mode, change noclip
             */
            if (Minecraft.getInstance().player.isCreative()) {
                NoClipHandler.setNoClip(!NoClipHandler.isNoClip());
                ClientUtils.displayMessge("No-Clip " + (NoClipHandler.isNoClip() ? "Enabled" : "Disabled"));
            }
        }
    }
}