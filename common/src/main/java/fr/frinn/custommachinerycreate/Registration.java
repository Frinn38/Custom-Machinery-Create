package fr.frinn.custommachinerycreate;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.frinn.custommachinery.api.ICustomMachineryAPI;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.guielement.GuiElementType;
import fr.frinn.custommachinery.api.requirement.RequirementType;
import fr.frinn.custommachinerycreate.components.ContraptionMachineComponent;
import fr.frinn.custommachinerycreate.components.ContraptionMachineComponent.Template;
import fr.frinn.custommachinerycreate.requirements.ContraptionRequirement;

public class Registration {
    public static final DeferredRegister<GuiElementType<?>> GUI_ELEMENTS = DeferredRegister.create(ICustomMachineryAPI.INSTANCE.modid(), GuiElementType.REGISTRY_KEY);
    public static final DeferredRegister<MachineComponentType<?>> MACHINE_COMPONENTS = DeferredRegister.create(ICustomMachineryAPI.INSTANCE.modid(), MachineComponentType.REGISTRY_KEY);
    public static final DeferredRegister<RequirementType<?>> REQUIREMENTS = DeferredRegister.create(ICustomMachineryAPI.INSTANCE.modid(), RequirementType.REGISTRY_KEY);

    public static final RegistrySupplier<MachineComponentType<ContraptionMachineComponent>> CONTRAPTION_MACHINE_COMPONENT = MACHINE_COMPONENTS.register("contraption", () -> MachineComponentType.create(Template.CODEC, ContraptionMachineComponent::new));

    public static final RegistrySupplier<RequirementType<ContraptionRequirement>> CONTRAPTION_REQUIREMENT = REQUIREMENTS.register("contraption", () -> RequirementType.world(ContraptionRequirement.CODEC));
}
