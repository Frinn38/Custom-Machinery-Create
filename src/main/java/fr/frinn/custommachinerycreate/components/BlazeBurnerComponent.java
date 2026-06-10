package fr.frinn.custommachinerycreate.components;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import fr.frinn.custommachinery.api.component.ComponentIOMode;
import fr.frinn.custommachinery.api.component.IMachineComponentManager;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.impl.component.AbstractMachineComponent;
import fr.frinn.custommachinerycreate.Registration;

public class BlazeBurnerComponent extends AbstractMachineComponent {

    public BlazeBurnerComponent(IMachineComponentManager manager) {
        super(manager, ComponentIOMode.INPUT);
    }

    @Override
    public MachineComponentType<BlazeBurnerComponent> getType() {
        return Registration.BLAZE_BURNER_MACHINE_COMPONENT.get();
    }

    public HeatLevel getHeatLevel() {
        return BlazeBurnerBlock.getHeatLevelOf(getManager().getLevel().getBlockState(getManager().getTile().getBlockPos().below()));
    }
}
