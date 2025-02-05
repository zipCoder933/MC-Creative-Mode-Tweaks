package org.zipcoder.cmt.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static org.zipcoder.cmt.CreativeModeTweaks.MODID;

public class MyModNetwork {
    public static final String PROTOCOL_VERSION = "1";

    /**
     * Our packet handler
     */
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;
    public static void registerPackets() {
        CHANNEL.registerMessage(packetId++,
            ToggleNoClipPacket.class,
            ToggleNoClipPacket::encode,
            ToggleNoClipPacket::decode,
            ToggleNoClipPacket::handle);
    }
}
