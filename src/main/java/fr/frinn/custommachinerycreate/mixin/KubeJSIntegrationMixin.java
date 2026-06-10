package fr.frinn.custommachinerycreate.mixin;

import fr.frinn.custommachinery.common.integration.kubejs.CustomCraftRecipeBuilderJS;
import fr.frinn.custommachinery.common.integration.kubejs.CustomMachineRecipeBuilderJS;
import fr.frinn.custommachinerycreate.integration.kubejs.BlazeBurnerRequirementJS;
import fr.frinn.custommachinerycreate.integration.kubejs.ContraptionRequirementJS;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({CustomMachineRecipeBuilderJS.class, CustomCraftRecipeBuilderJS.class})
public abstract class KubeJSIntegrationMixin implements BlazeBurnerRequirementJS, ContraptionRequirementJS {
}
