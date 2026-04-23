package fr.frinn.custommachinerycreate;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import fr.frinn.custommachinery.api.machine.MachineTile;
import fr.frinn.custommachinerycreate.components.ContraptionMachineComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(CustomMachineryCreate.MODID)
public class CustomMachineryCreate {

    public static final String MODID = "custommachinerycreate";

    public CustomMachineryCreate(final IEventBus MOD_BUS) {
        Registration.GUI_ELEMENTS.register(MOD_BUS);
        Registration.MACHINE_COMPONENTS.register(MOD_BUS);
        Registration.REQUIREMENTS.register(MOD_BUS);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static BlockEntity getFakeBE(BlockEntity blockEntity) {
        if(blockEntity instanceof MachineTile machine) {
            KineticBlockEntity fakeTile = machine.getComponentManager()
                    .getComponent(Registration.CONTRAPTION_MACHINE_COMPONENT.get())
                    .map(ContraptionMachineComponent::getFakeTile)
                    .orElse(null);
            if(fakeTile != null)
                return fakeTile;
        }
        return blockEntity;
    }
}
