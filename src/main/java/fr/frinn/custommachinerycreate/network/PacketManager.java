package fr.frinn.custommachinerycreate.network;

import fr.frinn.custommachinerycreate.CustomMachineryCreate;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = CustomMachineryCreate.MODID)
public class PacketManager {

    @SubscribeEvent
    public static void registerPackets(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(SUpdateFakeKineticTilePacket.TYPE, SUpdateFakeKineticTilePacket.CODEC, SUpdateFakeKineticTilePacket::handle);
    }
}
