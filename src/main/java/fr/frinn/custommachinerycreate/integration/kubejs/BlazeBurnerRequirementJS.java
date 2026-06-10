package fr.frinn.custommachinerycreate.integration.kubejs;

import com.simibubi.create.content.processing.recipe.HeatCondition;
import fr.frinn.custommachinery.api.integration.kubejs.RecipeJSBuilder;
import fr.frinn.custommachinerycreate.requirements.BlazeBurnerRequirement;

public interface BlazeBurnerRequirementJS extends RecipeJSBuilder {

    default RecipeJSBuilder requireHeatedBlazeBurner() {
        return this.addRequirement(new BlazeBurnerRequirement(HeatCondition.HEATED));
    }

    default RecipeJSBuilder requireSuperHeatedBlazeBurner() {
        return this.addRequirement(new BlazeBurnerRequirement(HeatCondition.SUPERHEATED));
    }
}
