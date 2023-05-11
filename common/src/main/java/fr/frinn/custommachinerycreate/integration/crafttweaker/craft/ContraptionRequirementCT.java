package fr.frinn.custommachinerycreate.integration.crafttweaker.craft;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import fr.frinn.custommachinery.api.integration.crafttweaker.RecipeCTBuilder;
import fr.frinn.custommachinery.api.integration.kubejs.RecipeJSBuilder;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinery.common.integration.crafttweaker.CTConstants;
import fr.frinn.custommachinery.common.integration.crafttweaker.CustomCraftRecipeCTBuilder;
import fr.frinn.custommachinerycreate.requirements.ContraptionRequirement;
import org.openzen.zencode.java.ZenCodeType.Expansion;
import org.openzen.zencode.java.ZenCodeType.Method;

@ZenRegister
@Expansion(CTConstants.RECIPE_BUILDER_CRAFT)
public class ContraptionRequirementCT {

    @Method
    public static CustomCraftRecipeCTBuilder requireSU(CustomCraftRecipeCTBuilder builder, float speed) {
        return requireSU(builder, speed, 0.0F);
    }

    @Method
    public static CustomCraftRecipeCTBuilder requireSU(CustomCraftRecipeCTBuilder builder, float speed, float stressImpact) {
        return addContraptionRequirement(builder, RequirementIOMode.INPUT, speed, stressImpact);
    }

    @Method
    public static CustomCraftRecipeCTBuilder produceSU(CustomCraftRecipeCTBuilder builder, float speed) {
        return produceSU(builder, speed, 0.0F);
    }

    @Method
    public static CustomCraftRecipeCTBuilder produceSU(CustomCraftRecipeCTBuilder builder, float speed, float stressCapacity) {
        return addContraptionRequirement(builder, RequirementIOMode.OUTPUT, speed, stressCapacity);
    }

    private static CustomCraftRecipeCTBuilder addContraptionRequirement(CustomCraftRecipeCTBuilder builder, RequirementIOMode mode, float speed, float stress) {
        if(speed < 0)
            return builder.error("Speed value cannot be negative: {}", speed);
        if(stress < 0)
            return builder.error("Stress value cannot be negative: {}", stress);
        return builder.addRequirement(new ContraptionRequirement(mode, speed, stress));
    }
}
