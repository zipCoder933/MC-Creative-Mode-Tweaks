package org.zipcoder.cmt.network.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.zipcoder.cmt.Config;

import java.util.function.Supplier;

public class ReplaceMessage {

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