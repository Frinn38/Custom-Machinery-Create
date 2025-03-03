package fr.frinn.custommachinerycreate.network;

import fr.frinn.custommachinerycreate.CustomMachineryCreate;
import fr.frinn.custommachinerycreate.client.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SUpdateFakeKineticTilePacket(BlockPos pos, CompoundTag nbt) implements CustomPacketPayload {

    public static final Type<SUpdateFakeKineticTilePacket> TYPE = new Type<>(CustomMachineryCreate.rl("update_fake_kinetic_tile"));

    public static final StreamCodec<FriendlyByteBuf, SUpdateFakeKineticTilePacket> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            SUpdateFakeKineticTilePacket::pos,
            ByteBufCodecs.COMPOUND_TAG,
            SUpdateFakeKineticTilePacket::nbt,
            SUpdateFakeKineticTilePacket::new
    );

    @Override
    public Type<SUpdateFakeKineticTilePacket> type() {
        return TYPE;
    }

    public static void handle(SUpdateFakeKineticTilePacket packet, IPayloadContext context) {
        if(context.flow().isClientbound()) {
            context.enqueueWork(() -> ClientPacketHandler.handleUpdateFakeTilePacket(packet.pos, packet.nbt));
        }
    }
}
