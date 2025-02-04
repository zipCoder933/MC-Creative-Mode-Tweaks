package org.zipcoder.cmt.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.zipcoder.cmt.utils.mixin.Player_I;

import java.util.function.Supplier;

public class ToggleNoClipPacket {

    boolean enabled;

    public ToggleNoClipPacket(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Serializes the packet
     *
     * @param packet
     * @param buf
     */
    public static void encode(ToggleNoClipPacket packet, FriendlyByteBuf buf) {
        buf.writeBoolean(packet.enabled);
    }

    /**
     * Deserializes the packet
     *
     * @param buf
     * @return
     */
    public static ToggleNoClipPacket decode(FriendlyByteBuf buf) {
        boolean enabled = buf.readBoolean();
        return new ToggleNoClipPacket(enabled);
    }

    public static void handle(ToggleNoClipPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
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
