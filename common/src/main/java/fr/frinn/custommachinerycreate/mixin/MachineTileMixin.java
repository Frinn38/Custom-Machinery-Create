package fr.frinn.custommachinerycreate.mixin;

import com.simibubi.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.contraptions.goggles.IHaveHoveringInformation;
import com.simibubi.create.content.schematics.ISpecialBlockEntityItemRequirement;
import com.simibubi.create.content.schematics.ItemRequirement;
import com.simibubi.create.content.schematics.ItemRequirement.ItemUseType;
import com.simibubi.create.foundation.utility.IPartialSafeNBT;
import fr.frinn.custommachinery.api.machine.ICustomMachine;
import fr.frinn.custommachinery.api.machine.MachineTile;
import fr.frinn.custommachinery.common.init.CustomMachineItem;
import fr.frinn.custommachinerycreate.Registration;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(MachineTile.class)
public abstract class MachineTileMixin implements IHaveGoggleInformation, IHaveHoveringInformation, IPartialSafeNBT, ISpecialBlockEntityItemRequirement {

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
    public void writeSafe(CompoundTag nbt) {
        nbt.putString("machineID", this.getMachine().getId().toString());
    }

    @Override
    public ItemRequirement getRequiredItems(BlockState state) {
        return new ItemRequirement(new ItemRequirement.StrictNbtStackRequirement(CustomMachineItem.makeMachineItem(this.getMachine().getId()), ItemUseType.CONSUME));
    }
}
