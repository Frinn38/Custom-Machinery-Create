package fr.frinn.custommachinerycreate.forge;

import dev.architectury.platform.forge.EventBuses;
import fr.frinn.custommachinerycreate.CustomMachineryCreate;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CustomMachineryCreate.MODID)
public class CustomMachineryCreateForge {

    public CustomMachineryCreateForge() {
        final IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(CustomMachineryCreate.MODID, MOD_BUS);

        MOD_BUS.addGenericListener(Block.class, CustomMachineryCreateForge::registryEvent);
    }

    private static void registryEvent(final RegistryEvent<Block> event) {
        CustomMachineryCreate.init();
    }
}
