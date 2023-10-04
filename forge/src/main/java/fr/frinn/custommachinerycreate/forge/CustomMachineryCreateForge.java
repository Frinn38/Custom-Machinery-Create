package fr.frinn.custommachinerycreate.forge;

import dev.architectury.platform.forge.EventBuses;
import fr.frinn.custommachinerycreate.CustomMachineryCreate;
import net.minecraft.core.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

@Mod(CustomMachineryCreate.MODID)
public class CustomMachineryCreateForge {

    public CustomMachineryCreateForge() {
        final IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(CustomMachineryCreate.MODID, MOD_BUS);
        MOD_BUS.addListener(this::onRegister);

        //CustomMachineryCreate.init();
    }

    /**
     * Delay the initialization of the deferred registries because CM might not have created the registries at that time.
     */
    private void onRegister(final RegisterEvent event) {
        if(event.getRegistryKey() == Registry.BLOCK_REGISTRY)
            CustomMachineryCreate.init();
    }
}
