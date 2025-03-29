package org.zipcoder.cmt.client.utils;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.zipcoder.cmt.network.PacketHandler;
import org.zipcoder.cmt.network.packets.ToggleNoClipMessage;
import org.zipcoder.cmt.utils.mixin.Player_I;

@OnlyIn(Dist.CLIENT)
public class NoClipHandler {

    public static void setNoClip(boolean noClipEnabled) {
        if (Minecraft.getInstance().player.isCreative()) {
            Player_I player_i = (Player_I) Minecraft.getInstance().player;
            //Send the packet to the server
            PacketHandler.CHANNEL.sendToServer(new ToggleNoClipMessage(noClipEnabled));
            //Set the value on the client as well
            player_i.setNoClip(noClipEnabled);
        }
    }

    public static boolean isNoClip() {
        Player_I player_i = (Player_I) Minecraft.getInstance().player;
        return player_i.isNoClip();
    }
}
