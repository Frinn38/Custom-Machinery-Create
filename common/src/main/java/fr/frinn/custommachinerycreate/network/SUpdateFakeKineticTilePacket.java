package fr.frinn.custommachinerycreate.network;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import dev.architectury.utils.Env;
import fr.frinn.custommachinerycreate.client.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

public class SUpdateFakeKineticTilePacket extends BaseS2CMessage {

    private final BlockPos pos;
    private final CompoundTag nbt;

    public SUpdateFakeKineticTilePacket(BlockPos pos, CompoundTag nbt) {
        this.pos = pos;
        this.nbt = nbt;
    }

    @Override
    public MessageType getType() {
        return PacketManager.UPDATE_FAKE_TILE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
        buf.writeNbt(this.nbt);
    }

    public static SUpdateFakeKineticTilePacket read(FriendlyByteBuf buf) {
        return new SUpdateFakeKineticTilePacket(buf.readBlockPos(), buf.readNbt());
    }

    @Override
    public void handle(PacketContext context) {
        if(context.getEnvironment() == Env.CLIENT) {
            context.queue(() -> ClientPacketHandler.handleUpdateFakeTilePacket(this.pos, this.nbt));
        }
    }
}
