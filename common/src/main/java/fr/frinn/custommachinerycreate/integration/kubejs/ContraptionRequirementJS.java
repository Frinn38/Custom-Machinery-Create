package fr.frinn.custommachinerycreate.integration.kubejs;

import fr.frinn.custommachinery.api.integration.kubejs.RecipeJSBuilder;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinerycreate.requirements.ContraptionRequirement;

public interface ContraptionRequirementJS extends RecipeJSBuilder {

    default RecipeJSBuilder requireSU(float speed) {
        return requireSU(speed, 0.0F);
    }

    default RecipeJSBuilder requireSU(float speed, float stressImpact) {
        return addContraptionRequirement(RequirementIOMode.INPUT, speed, stressImpact);
    }

    default RecipeJSBuilder produceSU(float speed) {
        return produceSU(speed, 0.0F);
    }

    default RecipeJSBuilder produceSU(float speed, float stressCapacity) {
        return addContraptionRequirement(RequirementIOMode.OUTPUT, speed, stressCapacity);
    }

    default RecipeJSBuilder addContraptionRequirement(RequirementIOMode mode, float speed, float stress) {
        if(speed < 0)
            return error("Speed value cannot be negative: {}", speed);
        if(stress < 0)
            return error("Stress value cannot be negative: {}", stress);
        return addRequirement(new ContraptionRequirement(mode, speed, stress));
    }
}
