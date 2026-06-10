package fr.frinn.custommachinerycreate.mixin;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.api.equipment.goggles.IHaveHoveringInformation;
import com.simibubi.create.api.schematic.nbt.PartialSafeNBT;
import fr.frinn.custommachinery.api.machine.ICustomMachine;
import fr.frinn.custommachinery.api.machine.MachineTile;
import fr.frinn.custommachinerycreate.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(MachineTile.class)
public abstract class MachineTileMixin implements IHaveGoggleInformation, IHaveHoveringInformation, PartialSafeNBT {

    @Shadow(remap = false)
    public abstract ICustomMachine getMachine();

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return ((MachineTile)(Object)this).getComponentManager()
                .getComponent(Registration.CONTRAPTION_MACHINE_COMPONENT.get())
                .map(component -> component.getFakeTile().addToGoggleTooltip(tooltip, isPlayerSneaking))
                .orElse(false);
    }

    @Override
    public boolean addToTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return ((MachineTile)(Object)this).getComponentManager()
                .getComponent(Registration.CONTRAPTION_MACHINE_COMPONENT.get())
                .map(component -> component.getFakeTile().addToTooltip(tooltip, isPlayerSneaking))
                .orElse(false);
    }

    @Override
    public void writeSafe(CompoundTag nbt, HolderLookup.Provider registries) {
        nbt.putString("machineID", this.getMachine().getId().toString());
    }
}
