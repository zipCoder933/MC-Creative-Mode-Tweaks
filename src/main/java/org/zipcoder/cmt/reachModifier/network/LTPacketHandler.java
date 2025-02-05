package org.zipcoder.cmt.reachModifier.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.zipcoder.cmt.Config;
import org.zipcoder.cmt.reachModifier.AdjustRangeHelper;

import java.util.function.Supplier;

import static org.zipcoder.cmt.CreativeModeTweaks.MODID;

//https://mcforge.readthedocs.io/en/1.16.x/networking/simpleimpl/

public class LTPacketHandler {

    private static final String PROTOCOL_VERSION = "1";
    private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MODID), //name
            () -> PROTOCOL_VERSION, //protocol version
            //server accept
            (serverVersion) -> serverVersion.equals(NetworkRegistry.ACCEPTVANILLA) || serverVersion.equals(NetworkRegistry.ABSENT) || serverVersion.compareTo(PROTOCOL_VERSION) <= 0,
            //client accept
            (clientVersion) -> true
    );

    public static void init() {
        int id = 0;
        INSTANCE.messageBuilder(ReplaceMessage.class, id++)
                .encoder(ReplaceMessage::toBytes)
                .decoder(ReplaceMessage::new)
                .consumerMainThread(ReplaceMessage::handle)
                .add();
        INSTANCE.messageBuilder(AdjustRangeMessage.class, id++)
                .encoder(AdjustRangeMessage::toBytes)
                .decoder(AdjustRangeMessage::new)
                .consumerMainThread(AdjustRangeMessage::handle)
                .add();
    }

    public static void sendReplaceMessage(BlockPos pos, BlockState state, BlockState checkState) {
        INSTANCE.sendToServer(new ReplaceMessage(pos, state, checkState));
    }

    public static void sendReachRangeMessage(double dist) {
        INSTANCE.sendToServer(new AdjustRangeMessage(dist));
    }


    //Replace
    public static class ReplaceMessage {

        private final BlockPos pos;
        private final BlockState state;
        private final BlockState checkState;

        public ReplaceMessage(BlockPos pos, BlockState state, BlockState checkState) {
            this.pos = pos;
            this.state = state;
            this.checkState = checkState;
        }

        public ReplaceMessage(FriendlyByteBuf buf) {
            this(buf.readBlockPos(), Block.stateById(buf.readInt()), Block.stateById(buf.readInt()));
        }

        public void toBytes(FriendlyByteBuf buf) {
            buf.writeBlockPos(this.pos);
            buf.writeInt(Block.getId(state));
            buf.writeInt(Block.getId(checkState));
        }

        public void handle(Supplier<NetworkEvent.Context> ctx) {
            ctx.get().setPacketHandled(true);
            final ServerPlayer player = ctx.get().getSender();
            if (!player.isCreative() || player.level().isClientSide) return;
            // validation
            if (state.getBlock() == Blocks.AIR) {
                return;
            }
            double dist = player.getEyePosition(1.0F).distanceTo(new Vec3(pos.getX(), pos.getY(), pos.getZ()));
            if (dist > Config.MAX_RANGE.get()) {
                return;
            }
            if (player.level().getBlockState(pos) != checkState) {
                return;
            }
            //
            ctx.get().enqueueWork(() -> {
                player.level().setBlock(pos, state, 2);
            });
            return;
        }
    }

    // AdjustRange

    public static class AdjustRangeMessage {

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
                dist = Math.min(Config.MAX_RANGE.get(), dist);
                AdjustRangeHelper.changeRangeModifier(player, dist);
            });
            return;
        }
    }

}
