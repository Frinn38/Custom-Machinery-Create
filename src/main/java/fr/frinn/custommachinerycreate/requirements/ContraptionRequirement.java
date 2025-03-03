package fr.frinn.custommachinerycreate.requirements;

import com.simibubi.create.AllBlocks;
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
import fr.frinn.custommachinerycreate.components.ContraptionMachineComponent;
import net.minecraft.network.chat.Component;

import java.util.Locale;

public record ContraptionRequirement(RequirementIOMode mode, float speed, float stress, boolean scaling) implements IRequirement<ContraptionMachineComponent> {

    public static final NamedCodec<ContraptionRequirement> CODEC = NamedCodec.record(contraptionRequirementInstance ->
            contraptionRequirementInstance.group(
                    RequirementIOMode.CODEC.fieldOf("mode").forGetter(IRequirement::getMode),
                    NamedCodec.floatRange(0.0F, Float.MAX_VALUE).fieldOf("speed").forGetter(requirement -> requirement.speed),
                    NamedCodec.floatRange(0.0F, Float.MAX_VALUE).optionalFieldOf("stress", 0.0F).forGetter(requirement -> requirement.stress),
                    NamedCodec.BOOL.optionalFieldOf("scaling", false).forGetter(requirement -> requirement.scaling)
            ).apply(contraptionRequirementInstance, ContraptionRequirement::new), "Contraption requirement"
    );

    @Override
    public RequirementType<ContraptionRequirement> getType() {
        return Registration.CONTRAPTION_REQUIREMENT.get();
    }

    @Override
    public MachineComponentType<ContraptionMachineComponent> getComponentType() {
        return Registration.CONTRAPTION_MACHINE_COMPONENT.get();
    }

    @Override
    public RequirementIOMode getMode() {
        return this.mode;
    }

    @Override
    public void gatherRequirements(IRequirementList<ContraptionMachineComponent> list) {
        list.processOnStart(this::processStart);
        list.processEachTick(this::processTick);
        list.processOnEnd(this::processEnd);
    }

    @Override
    public boolean test(ContraptionMachineComponent component, ICraftingContext context) {
        int speed = (int)context.getModifiedValue(this.speed, this, null);
        if(getMode() == RequirementIOMode.INPUT)
            return (component.getFakeTile().getTheoreticalSpeed() >= speed || component.getFakeTile().getTheoreticalSpeed() <= -speed)
                    && !component.getFakeTile().isOverStressed();
        else
            return true;
    }

    public CraftingResult processStart(ContraptionMachineComponent component, ICraftingContext context) {
        float speed = (float)context.getModifiedValue(this.speed, this, null);
        float stress = (float)context.getModifiedValue(this.stress, this, "stress");
        if(getMode() == RequirementIOMode.INPUT) {
            component.set(0, 0.0F, stress);
            if(this.scaling)
                context.setBaseSpeed(Math.abs(component.getFakeTile().getTheoreticalSpeed()) / speed);
        } else
            component.set(speed, stress, 0.0F);
        return CraftingResult.pass();
    }

    public CraftingResult processEnd(ContraptionMachineComponent component, ICraftingContext context) {
        component.markForReset();
        return CraftingResult.pass();
    }

    public CraftingResult processTick(ContraptionMachineComponent component, ICraftingContext context) {
        if(getMode() != RequirementIOMode.INPUT)
            return CraftingResult.pass();

        float speed = (float)context.getModifiedValue(this.speed, this, null);
        if(Math.abs(component.getFakeTile().getTheoreticalSpeed()) < speed)
            return CraftingResult.error(Component.translatable("custommachinerycreate.requirement.contraption.error.speed.input", speed, Math.abs(component.getFakeTile().getTheoreticalSpeed())));
        else if(component.getFakeTile().isOverStressed())
            return CraftingResult.error(Component.translatable("custommachinerycreate.requirement.contraption.error.stress.input"));

        if(this.scaling)
            context.setBaseSpeed(Math.abs(component.getFakeTile().getTheoreticalSpeed()) / speed);

        return CraftingResult.success();
    }

    @Override
    public void getDefaultDisplayInfo(IDisplayInfo info, RecipeRequirement<?, ?> requirement) {
        info.setItemIcon(AllBlocks.COGWHEEL.asStack());
        String mode = getMode().name().toLowerCase(Locale.ROOT);
        info.addTooltip(Component.translatable("custommachinerycreate.requirement.contraption.info.speed." + mode, this.speed));
        if(this.stress != 0)
            info.addTooltip(Component.translatable("custommachinerycreate.requirement.contraption.info.stress." + mode, this.stress));
        if(this.scaling)
            info.addTooltip(Component.translatable("custommachinerycreate.requirement.contraption.info.scaling"));
    }
}
