package fr.frinn.custommachinerycreate.mixin;

import com.simibubi.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.contraptions.goggles.IHaveHoveringInformation;
import fr.frinn.custommachinery.api.machine.MachineTile;
import fr.frinn.custommachinerycreate.Registration;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(MachineTile.class)
public class MachineTileMixin implements IHaveGoggleInformation, IHaveHoveringInformation {

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
}
