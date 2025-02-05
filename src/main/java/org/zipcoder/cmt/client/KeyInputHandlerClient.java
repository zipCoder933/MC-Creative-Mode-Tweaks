package org.zipcoder.cmt.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.zipcoder.cmt.client.utils.ClientUtils;
import org.zipcoder.cmt.network.PacketHandler;
import org.zipcoder.cmt.network.packets.ToggleNoClipMessage;
import org.zipcoder.cmt.utils.mixin.Player_I;

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

                Player_I player_i = (Player_I) Minecraft.getInstance().player;

                boolean noClipEnabled = !player_i.isNoClip();
                ClientUtils.displayMessge("No-Clip " + (noClipEnabled ? "Enabled" : "Disabled"));
                //Send the packet to the server
                PacketHandler.CHANNEL.sendToServer(new ToggleNoClipMessage(noClipEnabled));
                //Set the value on the client as well
                player_i.setNoClip(noClipEnabled);
            }
        }
    }
}