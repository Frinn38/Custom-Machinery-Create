package fr.frinn.custommachinerycreate.mixin;

import fr.frinn.custommachinery.common.integration.kubejs.CustomCraftRecipeJSBuilder;
import fr.frinn.custommachinery.common.integration.kubejs.CustomMachineRecipeBuilderJS;
import fr.frinn.custommachinerycreate.integration.kubejs.ContraptionRequirementJS;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({CustomMachineRecipeBuilderJS.class, CustomCraftRecipeJSBuilder.class})
public abstract class KubeJSIntegrationMixin implements ContraptionRequirementJS {
}
