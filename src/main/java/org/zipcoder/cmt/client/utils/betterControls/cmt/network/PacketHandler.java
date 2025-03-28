package org.zipcoder.cmt.client.utils.betterControls.cmt.network;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.zipcoder.cmt.client.utils.betterControls.cmt.network.packets.AdjustRangeMessage;
import org.zipcoder.cmt.client.utils.betterControls.cmt.network.packets.ReplaceMessage;
import org.zipcoder.cmt.client.utils.betterControls.cmt.network.packets.ToggleNoClipMessage;

import static org.zipcoder.cmt.CreativeModeTweaks.MODID;

public class PacketHandler {
    public static final String PROTOCOL_VERSION = "1";

    /**
     * Our packet handler
     */
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MODID), //name
            () -> PROTOCOL_VERSION, //protocol version
            PROTOCOL_VERSION::equals,     //server accept (serverVersion.equals(NetworkRegistry.ACCEPTVANILLA) || serverVersion.compareTo(PROTOCOL_VERSION) <= 0)
            PROTOCOL_VERSION::equals  //client accept
    );

    private static int packetId = 0;

    public static void registerPackets() {
        CHANNEL.registerMessage(packetId++,
                ToggleNoClipMessage.class,
                ToggleNoClipMessage::encode,
                ToggleNoClipMessage::decode,
                ToggleNoClipMessage::handle);
        CHANNEL.messageBuilder(ReplaceMessage.class, packetId++)
                .encoder(ReplaceMessage::toBytes)
                .decoder(ReplaceMessage::new)
                .consumerMainThread(ReplaceMessage::handle)
                .add();
        CHANNEL.messageBuilder(AdjustRangeMessage.class, packetId++)
                .encoder(AdjustRangeMessage::toBytes)
                .decoder(AdjustRangeMessage::new)
                .consumerMainThread(AdjustRangeMessage::handle)
                .add();
    }

    public static void sendReplaceMessage(BlockPos pos, BlockState state, BlockState checkState) {
        CHANNEL.sendToServer(new ReplaceMessage(pos, state, checkState));
    }

    public static void sendReachRangeMessage(double dist) {
        CHANNEL.sendToServer(new AdjustRangeMessage(dist));
    }
}
