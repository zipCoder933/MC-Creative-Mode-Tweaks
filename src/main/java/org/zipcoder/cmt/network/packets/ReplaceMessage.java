package org.zipcoder.cmt.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.zipcoder.cmt.Config;

import static org.zipcoder.cmt.CreativeModeTweaks.MODID;

public record ReplaceMessage(BlockPos pos, BlockState state, BlockState checkState) implements CustomPacketPayload {


    public static final CustomPacketPayload.Type<ToggleNoClipMessage> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(MODID, "toggle_no_clip"));

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, ReplaceMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_LONG,
            ReplaceMessage::writeBlockPos,
    ReplaceMessage::new
    );

    public static void writeBlockPos(ByteBuf buffer, BlockPos pos) {
        buffer.writeLong(pos.asLong());
    }
//
//    public void toBytes(FriendlyByteBuf buf) {
//        buf.writeBlockPos(this.pos);
//        buf.writeInt(Block.getId(state));
//        buf.writeInt(Block.getId(checkState));
//    }

    public static void handleDataOnMain(final ReplaceMessage packet, final IPayloadContext ctx) {
        Player player = ctx.player();
        if (!player.isCreative() || player.level().isClientSide) return;
        // validation
        if (packet.state.getBlock() == Blocks.AIR) {
            return;
        }
        double dist = player.getEyePosition(1.0F).distanceTo(new Vec3(packet.pos.getX(), packet.pos.getY(), packet.pos.getZ()));
        if (dist > Config.REACH_MAX_RANGE.get()) {
            return;
        }
        if (player.level().getBlockState(packet.pos) != packet.checkState) {
            return;
        }
        //
        ctx.enqueueWork(() -> {
            player.level().setBlock(packet.pos, packet.state, 2);
        });
    }
}