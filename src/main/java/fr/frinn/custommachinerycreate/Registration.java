package fr.frinn.custommachinerycreate;

import fr.frinn.custommachinery.api.ICustomMachineryAPI;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.guielement.GuiElementType;
import fr.frinn.custommachinery.api.requirement.RequirementType;
import fr.frinn.custommachinerycreate.components.ContraptionMachineComponent;
import fr.frinn.custommachinerycreate.components.ContraptionMachineComponent.Template;
import fr.frinn.custommachinerycreate.requirements.ContraptionRequirement;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class Registration {

    public static final DeferredRegister<GuiElementType<?>> GUI_ELEMENTS = DeferredRegister.create(GuiElementType.REGISTRY_KEY, ICustomMachineryAPI.INSTANCE.modid());
    public static final DeferredRegister<MachineComponentType<?>> MACHINE_COMPONENTS = DeferredRegister.create(MachineComponentType.REGISTRY_KEY, ICustomMachineryAPI.INSTANCE.modid());
    public static final DeferredRegister<RequirementType<?>> REQUIREMENTS = DeferredRegister.create(RequirementType.REGISTRY_KEY, ICustomMachineryAPI.INSTANCE.modid());


    public static final Supplier<MachineComponentType<ContraptionMachineComponent>> CONTRAPTION_MACHINE_COMPONENT = MACHINE_COMPONENTS.register("contraption", () -> MachineComponentType.create(Template.CODEC, ContraptionMachineComponent::new));

    public static final Supplier<RequirementType<ContraptionRequirement>> CONTRAPTION_REQUIREMENT = REQUIREMENTS.register("contraption", () -> RequirementType.world(ContraptionRequirement.CODEC));
}
