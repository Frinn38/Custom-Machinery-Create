package fr.frinn.custommachinerycreate.network;

import dev.architectury.networking.simple.MessageType;
import dev.architectury.networking.simple.SimpleNetworkManager;
import fr.frinn.custommachinerycreate.CustomMachineryCreate;

public class PacketManager {

    public static final SimpleNetworkManager MANAGER = SimpleNetworkManager.create(CustomMachineryCreate.MODID);

    public static final MessageType UPDATE_FAKE_TILE = MANAGER.registerS2C("update_fake_tile", SUpdateFakeKineticTilePacket::read);

    public static void init() {}
}
