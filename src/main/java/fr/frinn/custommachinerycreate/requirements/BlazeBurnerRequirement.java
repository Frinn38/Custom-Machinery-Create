package fr.frinn.custommachinerycreate.requirements;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.utility.CreateLang;
import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.crafting.CraftingResult;
import fr.frinn.custommachinery.api.crafting.ICraftingContext;
import fr.frinn.custommachinery.api.crafting.IRequirementList;
import fr.frinn.custommachinery.api.integration.jei.IDisplayInfo;
import fr.frinn.custommachinery.api.requirement.IRequirement;
import fr.frinn.custommachinery.api.requirement.RecipeRequirement;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinery.api.requirement.RequirementType;
import fr.frinn.custommachinerycreate.Registration;
import fr.frinn.custommachinerycreate.components.BlazeBurnerComponent;
import net.minecraft.network.chat.Component;

public record BlazeBurnerRequirement(HeatCondition heat) implements IRequirement<BlazeBurnerComponent> {

    public static final NamedCodec<BlazeBurnerRequirement> CODEC = NamedCodec.record(blazeBurnerRequirementInstance ->
            blazeBurnerRequirementInstance.group(
                    NamedCodec.enumCodec(HeatCondition.class).fieldOf("heat").forGetter(BlazeBurnerRequirement::heat)
            ).apply(blazeBurnerRequirementInstance, BlazeBurnerRequirement::new), "Blaze burner requirement"
    );

    @Override
    public RequirementType<BlazeBurnerRequirement> getType() {
        return Registration.BLAZE_BURNER_REQUIREMENT.get();
    }

    @Override
    public MachineComponentType<BlazeBurnerComponent> getComponentType() {
        return Registration.BLAZE_BURNER_MACHINE_COMPONENT.get();
    }

    @Override
    public RequirementIOMode getMode() {
        return RequirementIOMode.INPUT;
    }

    @Override
    public boolean test(BlazeBurnerComponent component, ICraftingContext context) {
        return this.heat.testBlazeBurner(component.getHeatLevel());
    }

    @Override
    public void gatherRequirements(IRequirementList<BlazeBurnerComponent> list) {
        list.worldCondition(((component, context) -> {
            if(this.heat.testBlazeBurner(component.getHeatLevel()))
                return CraftingResult.success();
            return CraftingResult.error(Component.translatable("custommachinerycreate.requirement.blazeburner.info", CreateLang.translateDirect(this.heat.getTranslationKey()).withColor(this.heat.getColor())));
        }));
    }

    @Override
    public void getDefaultDisplayInfo(IDisplayInfo info, RecipeRequirement<?, ?> requirement) {
        info.setItemIcon(AllBlocks.BLAZE_BURNER.asStack());
        info.addTooltip(Component.translatable("custommachinerycreate.requirement.blazeburner.info", CreateLang.translateDirect(this.heat.getTranslationKey()).withColor(this.heat.getColor())));
    }
}
