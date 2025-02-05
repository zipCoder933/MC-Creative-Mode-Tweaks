package org.zipcoder.cmt.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.zipcoder.cmt.Config;
import org.zipcoder.cmt.utils.MathUtils;
import org.zipcoder.cmt.utils.reach.AdjustRangeHelper;

import java.util.function.Supplier;

public class AdjustRangeMessage {

    private double dist;

    public AdjustRangeMessage(double dist) {
        this.dist = dist;
    }

    public AdjustRangeMessage(FriendlyByteBuf buf) {
        this(buf.readDouble());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.dist);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        final ServerPlayer player = ctx.get().getSender();
        if (!player.isCreative()) {
            return;
        }
        ctx.get().enqueueWork(() -> {
            if (dist < 0) {
                return;
            }
            dist = MathUtils.clamp(dist, Config.REACH_MIN_RANGE.get(), Config.REACH_MAX_RANGE.get());
            AdjustRangeHelper.changeRangeModifier(player, dist);
        });
        return;
    }
}