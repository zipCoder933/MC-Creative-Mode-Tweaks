package org.zipcoder.cmt.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.zipcoder.cmt.utils.mixin.Player_I;

import static org.zipcoder.cmt.CreativeModeTweaks.MODID;

public record ToggleNoClipMessage(boolean enabled) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ToggleNoClipMessage> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(MODID, "toggle_no_clip"));

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // 'name' will be encoded and decoded as a string
    // 'age' will be encoded and decoded as an integer
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, ToggleNoClipMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ToggleNoClipMessage::enabled,
            ToggleNoClipMessage::new
    );


    public static void handleDataOnMain(final ToggleNoClipMessage packet, final IPayloadContext ctx) {
        Player player = ctx.player();
        ctx.enqueueWork(() -> {
            if (player != null) {
                Player_I player_i = (Player_I) player;
                player_i.setNoClip(packet.enabled);

                System.out.println("SERVER:" +
                        " NoClip: " + player_i.isNoClip()
                        + " player: " + player.getName().getString());
            }
        });
    }

}
