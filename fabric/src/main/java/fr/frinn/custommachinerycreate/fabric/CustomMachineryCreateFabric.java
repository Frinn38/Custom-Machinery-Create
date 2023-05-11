package fr.frinn.custommachinerycreate.fabric;

import fr.frinn.custommachinerycreate.CustomMachineryCreate;
import net.fabricmc.api.ModInitializer;

public class CustomMachineryCreateFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CustomMachineryCreate.init();
    }
}
