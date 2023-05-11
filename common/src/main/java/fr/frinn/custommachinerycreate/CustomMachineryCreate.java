package fr.frinn.custommachinerycreate;

import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import fr.frinn.custommachinerycreate.client.ClientHandler;
import fr.frinn.custommachinerycreate.network.PacketManager;

public class CustomMachineryCreate {

    public static final String MODID = "custommachinerycreate";

    public static void init() {
        Registration.GUI_ELEMENTS.register();
        Registration.MACHINE_COMPONENTS.register();
        Registration.REQUIREMENTS.register();

        PacketManager.init();

        EnvExecutor.runInEnv(Env.CLIENT, () -> ClientHandler::init);
    }
}
