package org.zipcoder.cmt.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.zipcoder.cmt.network.packets.ToggleNoClipMessage;

public class ServerPayloadHandler {

    public static void handleDataOnMain(final ToggleNoClipMessage data, final IPayloadContext context) {
        ToggleNoClipMessage.handleDataOnMain(data, context);
    }
}
