package fr.frinn.custommachinerycreate.client;

import fr.frinn.custommachinery.client.screen.creation.component.RegisterComponentBuilderEvent;
import fr.frinn.custommachinerycreate.CustomMachineryCreate;
import fr.frinn.custommachinerycreate.Registration;
import fr.frinn.custommachinerycreate.client.builder.ContraptionComponentBuilder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = CustomMachineryCreate.MODID, dist = Dist.CLIENT)
public class ClientHandler {

    public ClientHandler(final IEventBus MOD_BUS) {
        MOD_BUS.addListener(this::registerMachineComponentBuilders);
    }

    private void registerMachineComponentBuilders(final RegisterComponentBuilderEvent event) {
        event.register(Registration.CONTRAPTION_MACHINE_COMPONENT.get(), new ContraptionComponentBuilder());
    }
}
