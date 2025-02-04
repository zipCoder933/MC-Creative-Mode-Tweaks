package org.zipcoder.cmt.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.zipcoder.cmt.client.utils.ClientUtils;
import org.zipcoder.cmt.network.MyModNetwork;
import org.zipcoder.cmt.network.ToggleNoClipPacket;
import org.zipcoder.cmt.utils.mixin.Player_I;

import static org.zipcoder.cmt.client.ClientModEvents.KEY_TOGGLE_NOCLIP;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class KeyInputHandlerClient {

    private static boolean noClipEnabled = false;

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KEY_TOGGLE_NOCLIP.isDown()) {
            noClipEnabled = !noClipEnabled;
            ClientUtils.displayMessge("No-Clip " + (noClipEnabled ? "Enabled" : "Disabled"));

            //Send the packet to the server
            MyModNetwork.CHANNEL.sendToServer(new ToggleNoClipPacket(noClipEnabled));
            //Set the value on the client as well
            Player_I player_i = (Player_I) Minecraft.getInstance().player;
            player_i.setNoClip(noClipEnabled);
        }
    }
}