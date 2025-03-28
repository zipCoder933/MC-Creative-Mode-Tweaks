package org.zipcoder.cmt.client.utils.betterControls.cmt.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.zipcoder.cmt.client.utils.betterControls.cmt.utils.mixin.Player_I;

import java.util.function.Supplier;

public class ToggleNoClipMessage {

    boolean enabled;

    public ToggleNoClipMessage(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Serializes the packet
     *
     * @param packet
     * @param buf
     */
    public static void encode(ToggleNoClipMessage packet, FriendlyByteBuf buf) {
        buf.writeBoolean(packet.enabled);
    }

    /**
     * Deserializes the packet
     *
     * @param buf
     * @return
     */
    public static ToggleNoClipMessage decode(FriendlyByteBuf buf) {
        boolean enabled = buf.readBoolean();
        return new ToggleNoClipMessage(enabled);
    }

    public static void handle(ToggleNoClipMessage packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            // This code runs on the server thread.
            ServerPlayer player = ctx.getSender();
            if (player != null) {
                Player_I player_i = (Player_I) player;
                player_i.setNoClip(packet.enabled);

                System.out.println("SERVER:" +
                        " NoClip: " + player_i.isNoClip()
                        + " player: " + player.getName().getString());
//                player.getAbilities().setFlyingSpeed();
            }
        });
        // Mark the packet as handled
        ctx.setPacketHandled(true);
    }
}
